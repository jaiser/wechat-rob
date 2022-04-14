package com.jaiser.wechatrob.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jaiser.wechatrob.domain.AutoReplyD;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoReplyMapper extends BaseMapper<AutoReplyD> {

    @Select("SELECT * FROM `auto_reply_d` WHERE CHAT_KEY = #{chatKey}")
    AutoReplyD selectOneByKey(@Param("chatKey")String chatKey);

}
