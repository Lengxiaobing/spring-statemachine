package com.demo.statemachine.entity.event;

/**
 * 文件名: FormEvents.java
 * 作者: zhuxiang
 * 时间: 2020/8/28 16:16
 * 描述: 表单状态改变的事件
 */
public enum FormEvents {
    /**
     * 表单状态改变的事件
     */
    WRITE(1, "WRITE", "填写"),
    CONFIRM(2, "CONFIRM", "校验"),
    SUBMIT(3, "SUBMIT ", "提交");

    private Integer code;
    private String msg;
    private String desc;

    FormEvents(Integer code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static FormEvents getInstance(Integer code) {
        FormEvents[] fromEvents = values();
        for (FormEvents event : fromEvents) {
            if (event.getCode().equals(code)) {
                return event;
            }
        }
        return null;
    }
}
