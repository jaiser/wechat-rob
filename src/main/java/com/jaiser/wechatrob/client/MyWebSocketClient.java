package com.jaiser.wechatrob.client;

import com.alibaba.fastjson.JSONObject;
import com.jaiser.wechatrob.client.group.MyGroupUtil;
import com.jaiser.wechatrob.client.group.QueueList;
import com.jaiser.wechatrob.domain.WXMsg;
import com.jaiser.wechatrob.domain.WXRecMsg;
import com.jaiser.wechatrob.enums.GroupOperateEnum;
import com.jaiser.wechatrob.enums.ReconnectThreadEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 微信ws连接
 *
 * @author Jaiser on 2022/4/14
 */
@Slf4j
public class MyWebSocketClient extends WebSocketClient {

    @Autowired
    private MyGroupUtil myGroupUtil;

    public static boolean WEBSTATUS = false;

    private static final int HEART_BEAT = 5005;             //服务器返回心跳包
    private static final int RECV_TXT_MSG = 1;              //收到的消息为文字消息
    private static final int RECV_PIC_MSG = 3;              //收到的消息为图片消息
    private static final int USER_LIST = 5000;              //发送消息类型为获取用户列表
    private static final int GET_USER_LIST_SUCCSESS = 5001; //获取用户列表成功
    private static final int GET_USER_LIST_FAIL     = 5002; //获取用户列表失败
    private static final int TXT_MSG = 555;                 //发送消息类型为文本
    private static final int PIC_MSG = 500;                 //发送消息类型为图片
    private static final int AT_MSG = 550;                  //发送群中@用户的消息
    private static final int CHATROOM_MEMBER = 5010;        //获取群成员
    private static final int CHATROOM_MEMBER_NICK = 5020;
    private static final int PERSONAL_INFO = 6500;
    private static final int DEBUG_SWITCH = 6000;
    private static final int PERSONAL_DETAIL =6550;
    private static final int DESTROY_ALL = 9999;

    private static final String ROOM_MEMBER_LIST = "op:list member";
    private static final String CONTACT_LIST = "user list";
    private static final String NULL_MSG = "null";


    /**
     * 初始化
     *
     * @param serverUri
     */
    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    /**
     * 连接成功
     * @param serverHandshake
     */
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("正在建立连接......");
        WEBSTATUS = true;
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.error("连接断开，尝试重连！");
        // 重连
        WEBSTATUS = false;
        ReconnectThreadEnum.getInstance().reconnectWs(this);
    }

    @Override
    public void onError(Exception e) {
        log.info("服务器发生异常！");
        log.info(e.getMessage());
        WEBSTATUS = false;
    }



    /**
     * 在这里进行消息监听
     * @param s
     */
    @Override
    public void onMessage(String s) {
        //在这里编写应答的一些代码
        //可以在这里通过正则，或者是发送的消息类型进行判断，进行一些文字回复
        //也可以在这里调用的其他的接口，可以使用Utils包下面的NetPostRequest进行相应的调用

        //注意对sender为ROOT时的消息进行过滤，还有对公众号的消息进行过滤(gh_xxxxxx)
        log.info("接收到的消息 --> " + s);
        WXRecMsg wxRecMsg = JSONObject.parseObject(s, WXRecMsg.class);
        if (wxRecMsg.getType() == RECV_TXT_MSG && StringUtils.isNotBlank(wxRecMsg.getContent())) {
            String msg = wxRecMsg.getContent();
            String[] msgList = msg.split("\\s+");
            String message = null;
            if (GroupOperateEnum.ORDER_LIST.getValue().equals(msgList[0])) {
                message = myGroupUtil.listAllOrder();
            } else if (GroupOperateEnum.ALL_ENTRY.getValue().equals(msgList[0])) {
                message = myGroupUtil.listAllReply();
            } else if (GroupOperateEnum.STUDY_ENTRY.getValue().equals(msgList[0])) {
                message = myGroupUtil.studyEntry(wxRecMsg);
            } else if (GroupOperateEnum.UPDATE_ENTRY.getValue().equals(msgList[0])) {
                message = myGroupUtil.updateEntry(wxRecMsg);
            }else {
                if (isRepeat(msg)) {
                    message = wxRecMsg.getContent();
                }else {
                    message = myGroupUtil.replyEntry(wxRecMsg);
                }
            }
            if (StringUtils.isNotBlank(message)) {
                sendTextMsg(wxRecMsg.getWxid(), message);
            }
        }

    }


    /**
     * 发送信息
     * @param json 要发送信息的json字符串
     */
    private void sendMsg(String json) {
        try {
            send(json);
        } catch (Exception e) {
            //发送消息失败！
            log.info("发送消息失败！");
            log.info(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取会话ID
     * @return
     */
    private String getSessionId(){
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 发送文本消息
     * @param wxid 个人的wxid或者群id（xxx@chatroom）
     * @param text 要发送的消息内容
     */
    public void sendTextMsg(String wxid, String text){
        //创建发送消息JSON
        String json = WXMsg.builder()
                .content(text)
                .wxid(wxid)
                .type(TXT_MSG)
                .id(getSessionId())
                .roomid(wxid)
                .nickname("rob")
                .ext("rob")
                .build()
                .toJson();
//        Map map = new HashMap<>();
//        map.put("para", json);
        log.info("发送文本消息 --> " + json);
        sendMsg(json);
    }

    /**
     * 发送图片消息
     * @param wxid  个人的wxid或者群id（xxx@chatroom）
     * @param imgUrlStr 发送图片的绝对路径
     */
    public void sendImgMsg(String wxid, String imgUrlStr) {
        //创建发送消息JSON
        String json = WXMsg.builder()
                .content(imgUrlStr)
                .wxid(wxid)
                .type(PIC_MSG)
                .id(getSessionId())
                .roomid(wxid)
                .nickname("rob")
                .ext("rob")
                .build()
                .toJson();
        log.info("发送图片消息 --> " + json);
        sendMsg(json);
    }

    /**
     * 发送AT类型消息 ---> 暂不可用
     */
    public void sendAtMsg(String wxid, String roomId, String text){
        //创建发送消息JSON
        String json = WXMsg.builder()
                .content(text)
                .wxid(wxid)
                .roomid(roomId)
                .type(AT_MSG)
                .id(getSessionId())
                .nickname("rob")
                .ext("rob")
                .build()
                .toJson();
        log.info("发送微信群AT成员消息 --> " + json);
        sendMsg(json);
    }

    /**
     * 获取联系人列表
     */
    public void getContactList() {
        //创建发送消息JSON
        String json = WXMsg.builder()
                .content(CONTACT_LIST)
                .wxid(NULL_MSG)
                .type(USER_LIST)
                .id(getSessionId())
                .build()
                .toJson();
        log.info("发送获取联系人列表请求 --> " + json);
        sendMsg(json);
    }

    /**
     * 获取所有群成员列表
     */
    public void getRoomMemberList() {
        //创建发送消息JSON
        String json = WXMsg.builder()
                .content(ROOM_MEMBER_LIST)
                .wxid(NULL_MSG)
                .type(CHATROOM_MEMBER)
                .id(getSessionId())
                .build()
                .toJson();
        log.info("发送获取所有群成员列表请求 --> " + json);
        sendMsg(json);
    }


    // 缓存聊天队列
    QueueList<String> queueList = new QueueList(2);

    private String lastestRepeat = null;

    private Boolean isRepeat(String msg) {
        if (StringUtils.isBlank(msg)) {
            return false;
        }
        queueList.enQueue(msg);
        if (queueList.isFull() && queueList.isCongruent() && !msg.equals(lastestRepeat)) {
            lastestRepeat = msg;
            return true;
        }
        return false;
    }


}