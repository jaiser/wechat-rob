package com.jaiser.wechatrob.client.group;

import com.jaiser.wechatrob.domain.AutoReplyD;
import com.jaiser.wechatrob.domain.WXRecMsg;
import com.jaiser.wechatrob.enums.GroupOperateEnum;
import com.jaiser.wechatrob.service.AutoReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 自定义群组监听工具
 *
 * @author Jaiser on 2022/4/6
 */
@Component
public class MyGroupUtil {

    /**
     * 日志对象
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    public AutoReplyService autoReplyService;

    /**
     * 列出所有指令
     *
     */
    public String listAllOrder() {
        StringBuffer sb = new StringBuffer("以下为目前可用指令：");
        for (GroupOperateEnum operateEnum :
                GroupOperateEnum.values()) {
//            if (operateEnum.isShow()) {
                sb.append("\n《" + operateEnum.getValue() + "》" + operateEnum.getRemark());
//            }
        }
        return sb.toString();
    }

    /**
     * 查询组词条
     *
     * @param wxRecMsg
     */
    public String replyEntry(WXRecMsg wxRecMsg) {

        // 事件发生的群
        AutoReplyD autoReplyD = autoReplyService.selectOneByKey(wxRecMsg.getContent());
        if (autoReplyD != null) {
            return autoReplyD.getChatValue();
        }
        return null;
    }

    /**
     * 查询回复词条
     */
    public String listAllReply() {
        AutoReplyD autoReplyD = new AutoReplyD();
        List<AutoReplyD> infoList = autoReplyService.listReplyInfo(autoReplyD);
        String res = null;
        if (infoList != null && infoList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (AutoReplyD vm :
                    infoList) {
                sb.append("《");
                sb.append(vm.getChatKey().trim());
                sb.append("》");
            }
            try {
                res = "共" + infoList.size() + "条词条，输入括号内关键字进行查询\n" + sb.toString();
            } catch (Exception e) {
                logger.error("查询构造词条异常", e);
                res = "查询词条异常，请联系管理员！";
            }
        } else {
            res = "暂无词条！";
        }
        return res;
    }

    /**
     * 学习词条
     *
     * @param wxRecMsg
     */
    public String studyEntry(WXRecMsg wxRecMsg) {

        String msg = wxRecMsg.getContent();
        String[] msgList = msg.split("\\s+");

        String messages = null;
        //发言人信息
        logger.info("学习对象key：《{}》，回复key：《{}》", msgList[0], msgList[1]);
//        if (!msgList[1].startsWith(".")) {
//            messages = Messages.getMessages(new At(Identifies.ID(String.valueOf(event.getAuthor().getId()))), Text.of("学习key必须要用.开头！"));
//
//        }   else {
            AutoReplyD autoReplyD = autoReplyService.selectOneByKey(msgList[1]);
            if (autoReplyD != null) {
                messages = "已经存在回复，请更新或查询！";
            } else {

                AutoReplyD vo = new AutoReplyD();
                vo.setReplyId(wxRecMsg.getWxid());
                vo.setReplyName(wxRecMsg.getWxid());

                vo.setInsertUserId(wxRecMsg.getWxid());
                vo.setInsertUserName(wxRecMsg.getWxid());
                vo.setChatKey(msgList[1].trim());
                vo.setChatValue(msgList[2].trim());
                vo.setType("1");
                if (autoReplyService.saveInfo(vo)) {
                    messages = "学习成功！";
                } else {
                    messages = "学习失败，请联系管理员！";
                }
//            }
        }
        return messages;
    }

    /**
     * 更新词条
     *
     * @param wxRecMsg
     */
    public String updateEntry(WXRecMsg wxRecMsg) {


        String msg = wxRecMsg.getContent();
        String[] msgList = msg.split("\\s+");

        String messages = null;


        logger.info("更新对象key：《{}》，回复key：《{}》", msgList[0], msgList[1]);
        AutoReplyD autoReplyD = autoReplyService.selectOneByKey(msgList[1]);
        if (autoReplyD == null) {
            messages = "不存在该回复，请学习！";
        } else {
            AutoReplyD vo = new AutoReplyD();
            vo.setId(autoReplyD.getId());
            vo.setReplyId(wxRecMsg.getWxid());
            vo.setReplyName(wxRecMsg.getWxid());
            vo.setUpdateUserId(wxRecMsg.getWxid());
            vo.setUpdateUserName(wxRecMsg.getWxid());
            vo.setChatKey(msgList[1].trim());
            vo.setChatValue(msgList[2].trim());
            if (autoReplyService.updateInfo(vo)) {
                messages = "更新成功！";
            } else {
                messages = "更新失败，请联系管理员！";
            }
        }
        return messages;
    }


    /**
     * 清空所有缓存， 所有缓存都在这里进行统一清空
     */
    public void clearAllCache(){
        //清空回复缓存
        autoReplyService.clearReplyCache();
    }
}