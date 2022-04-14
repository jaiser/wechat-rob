package com.jaiser.wechatrob.domain;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WXMsg {
    private String id;
    private String wxid;
    private String content;
    private String roomid;
    private int type;
    private String nickname;
    private String ext;

    public String toJson() {
        return JSON.toJSONString(this);
    }
}
