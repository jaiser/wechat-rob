package com.jaiser.wechatrob.domain;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("auto_reply_d")
public class AutoReplyD {

  @TableField(value = "reply_id")
  private String replyId;
  @TableField(value = "reply_name")
  private String replyName;
  @TableField(value = "chat_key")
  private String chatKey;
  @TableField(value = "chat_value")
  private String chatValue;
  @TableField(value = "insert_user_name")
  private String insertUserName;
  @TableField(value = "insert_user_id")
  private String insertUserId;
  @TableField(value = "insert_time")
  private String insertTime;
  @TableField(value = "update_user_name")
  private String updateUserName;
  @TableField(value = "update_user_id")
  private String updateUserId;
  @TableField(value = "update_time")
  private String updateTime;
  @TableId(value = "id", type = IdType.AUTO)
  private String id;
  @TableField(value = "type")
  private String type;


  public String getReplyId() {
    return replyId;
  }

  public void setReplyId(String replyId) {
    this.replyId = replyId;
  }


  public String getReplyName() {
    return replyName;
  }

  public void setReplyName(String replyName) {
    this.replyName = replyName;
  }


  public String getChatKey() {
    return chatKey;
  }

  public void setChatKey(String chatKey) {
    this.chatKey = chatKey;
  }


  public String getChatValue() {
    return chatValue;
  }

  public void setChatValue(String chatValue) {
    this.chatValue = chatValue;
  }


  public String getInsertUserName() {
    return insertUserName;
  }

  public void setInsertUserName(String insertUserName) {
    this.insertUserName = insertUserName;
  }


  public String getInsertUserId() {
    return insertUserId;
  }

  public void setInsertUserId(String insertUserId) {
    this.insertUserId = insertUserId;
  }


  public String getInsertTime() {
    return insertTime;
  }

  public void setInsertTime(String insertTime) {
    this.insertTime = insertTime;
  }


  public String getUpdateUserName() {
    return updateUserName;
  }

  public void setUpdateUserName(String updateUserName) {
    this.updateUserName = updateUserName;
  }


  public String getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(String updateUserId) {
    this.updateUserId = updateUserId;
  }


  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "AutoReplyD{" +
            "replyId='" + replyId + '\'' +
            ", replyName='" + replyName + '\'' +
            ", chatKey='" + chatKey + '\'' +
            ", chatValue='" + chatValue + '\'' +
            ", insertUserName='" + insertUserName + '\'' +
            ", insertUserId='" + insertUserId + '\'' +
            ", insertTime='" + insertTime + '\'' +
            ", updateUserName='" + updateUserName + '\'' +
            ", updateUserId='" + updateUserId + '\'' +
            ", updateTime='" + updateTime + '\'' +
            ", id='" + id + '\'' +
            ", type='" + type + '\'' +
            '}';
  }
}
