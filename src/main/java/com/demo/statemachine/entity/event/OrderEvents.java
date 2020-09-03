package com.demo.statemachine.entity.event;

/**
 * 文件名: OrderEvents.java
 * 作者: zhuxiang
 * 时间: 2020/8/28 16:16
 * 描述: 订单状态改变的事件
 */
public enum OrderEvents {
    /**
     * 订单状态改变的事件
     */
    PAY(1, "PAY", "支付"),
    SEND(2, "SEND", "发货"),
    RECEIVE(3, "RECEIVE", "收货");

    private Integer code;
    private String msg;
    private String desc;

    OrderEvents(Integer code, String msg, String desc) {
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

    public static OrderEvents getInstance(Integer code) {
        OrderEvents[] orderEvents = values();
        for (OrderEvents events : orderEvents) {
            if (events.getCode().equals(code)) {
                return events;
            }
        }
        return null;
    }
}
