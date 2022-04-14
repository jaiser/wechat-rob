package com.jaiser.wechatrob.service;



import com.jaiser.wechatrob.domain.AutoReplyD;

import java.util.List;

/**
 * @author jaiser
 */
public interface AutoReplyService {

    /**
     * 根据key查询回复信息
     * @param chatKey
     * @return
     */
    public AutoReplyD selectOneByKey(String chatKey);

    /**
     * 更新信息
     * @param autoReplyD
     * @return
     */
    public Boolean updateInfo(AutoReplyD autoReplyD);


    /**
     * 保存信息
     * @param autoReplyD
     * @return
     */
    public Boolean saveInfo(AutoReplyD autoReplyD);

    /**
     * 查询回复信息里诶包
     * @param autoReplyD
     * @return
     */
    public List<AutoReplyD> listReplyInfo(AutoReplyD autoReplyD);

    /**
     * 清除缓存
     */
    public void clearReplyCache();
}
