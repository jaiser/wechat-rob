package com.jaiser.wechatrob.enums;

import com.jaiser.wechatrob.WechatRobApplication;
import com.jaiser.wechatrob.client.MyWebSocketClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 重连ws
 *
 * @author Jaiser on 2022/4/14
 */
@Slf4j
public enum ReconnectThreadEnum {


    WebSocketInstance(){
        @Override
        public void reconnectWs(MyWebSocketClient demoWebSocketClient) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //重连间隔一秒
                        Thread.sleep(5000);
                        log.error( "重新连接");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    demoWebSocketClient.reconnect();
                }
            });
        }
    };

    private static final ExecutorService cachedThreadPool = new ThreadPoolExecutor(1,1, 1000l, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    public abstract void reconnectWs(MyWebSocketClient demoWebSocketClient);

    public static ReconnectThreadEnum getInstance(){
        return WebSocketInstance;
    }
}