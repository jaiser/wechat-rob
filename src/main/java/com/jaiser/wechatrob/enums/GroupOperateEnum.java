package com.jaiser.wechatrob.enums;

/**
 * 群组操作枚举
 */
public enum GroupOperateEnum {

    ALL_ENTRY("词条", "输入 词条 查询所有词条关键字", true),
    ORDER_LIST("指令","输入 指令 查询所有指令",false),
    STUDY_ENTRY("学习","输入 学习 key repeat,例如:学习 你好 你也好",true),
    UPDATE_ENTRY("更新","输入 更新 key repeat,例如 更新 你好 收到",true),
    ;


    /**
     * 指令值
     */
    private String value;

    /**
     * 指令说明
     */
    private String remark;

    /**
     * 是否展示
     */
    private boolean isShow;


    GroupOperateEnum(String value, String remark, boolean isShow) {
        this.value = value;
        this.remark = remark;
        this.isShow = isShow;
    }

    @Override
    public String toString() {
        return "OperateEnum{" +
                "value='" + value + '\'' +
                ", remark='" + remark + '\'' +
                ", isShow=" + isShow +
                '}';
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
