package com.jaiser.wechatrob.domain;

import lombok.Data;

/**
 * 微信对象
 *
 * @author Jaiser on 2022/4/14
 */
@Data
public class WXRecMsg {

    private String id;

    /**
     * 微信id 群id或者个人id
     */
    private String wxid;

    /**
     * 收取到的消息
     */
    private String content;

    /**
     * 消息时间
     */
    private String time;

    /**
     * 消息类型
     */
    private int type;

    @Override
    public String toString() {
        return "WXRecMsg{" +
                "id='" + id + '\'' +
                ", wxid='" + wxid + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", type=" + type +
                '}';
    }
}