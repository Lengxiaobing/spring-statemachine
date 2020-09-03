package com.demo.statemachine.entity.state;

/**
 * 文件名: OrderStates.java
 * 作者: zhuxiang
 * 时间: 2020/8/28 16:16
 * 描述: 订单状态
 */
public enum OrderStates {
    /**
     * 订单状态
     */
    UNPAID(1, "UNPAID", "待支付"),
    WAITING_FOR_SEND(2, "WAITING_FOR_SEND", "待发货"),
    WAITING_FOR_RECEIVE(3, "WAITING_FOR_RECEIVE", "待收货"),
    DONE(4, "DONE", "结束");

    OrderStates(Integer code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    private Integer code;
    private String msg;
    private String desc;

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

    public static OrderStates getInstance(Integer value) {
        OrderStates[] orderStates = values();
        for (OrderStates state : orderStates) {
            if (state.getCode().equals(value)) {
                return state;
            }
        }
        return null;
    }

}
