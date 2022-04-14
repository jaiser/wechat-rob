package com.jaiser.wechatrob;

import com.jaiser.wechatrob.client.MyWebSocketClient;
import org.apache.logging.log4j.core.config.Scheduled;
import org.java_websocket.client.WebSocketClient;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@MapperScan(basePackages = "com.jaiser.wechatrob.mapper") //扫描所有的mapper接口并创建代理类
public class WechatRobApplication {

    private static final Logger logger = LoggerFactory.getLogger(WechatRobApplication.class);

    @Value("${wechat.url}")
    private String wsUrl;

    public static void main(String[] args) {
        SpringApplication.run(WechatRobApplication.class, args);
        startSuccess();
    }

    /**
     * 初始化启动日志
     */
    private static void startSuccess() {
        logger.info("=========================================");
        logger.info("=============启动成功，万事大吉=============");
        logger.info("=========================================");
    }

//    @PostConstruct
//    public void init() {
//        if(StringUtils.isEmpty(wsUrl)) {
//            throw new RuntimeException("wechat.url is empty");
//        }else {
//            webServiceClient();
//        }
//    }

    @Bean
    public WebSocketClient webServiceClient() {
        try {
            MyWebSocketClient mWebSocketClient = new MyWebSocketClient(new URI(wsUrl));
            mWebSocketClient.connect();
            return mWebSocketClient;
        } catch(URISyntaxException e){
            e.printStackTrace();
        }
        return null;
    }
}
