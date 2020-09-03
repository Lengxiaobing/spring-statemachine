package com.demo.statemachine.constant;

/**
 * 文件名: OrderConstant.java
 * 作者: zhuxiang
 * 时间: 2020/8/28 16:16
 * 描述: 订单状态
 */
public final class OrderStatesConstant {
    /**
     * 待支付
     */
    public static final String UNPAID = "UNPAID";
    /**
     * 待发货
     */
    public static final String WAITING_FOR_SEND = "WAITING_FOR_SEND";
    /**
     * 待收货
     */
    public static final String WAITING_FOR_RECEIVE = "WAITING_FOR_RECEIVE";
    /**
     * 结束
     */
    public static final String DONE = "DONE";

}
