package com.demo.statemachine.transition;

import com.demo.statemachine.constant.OrderStatesConstant;
import com.demo.statemachine.constant.StateMachineConstant;
import com.demo.statemachine.entity.event.OrderEvents;
import com.demo.statemachine.entity.state.OrderStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * 文件名: OrderMultipleTransition.java
 * 作者: zhuxiang
 * 时间: 2020/8/28 17:38
 * 描述: 多流程状态机监听器
 */
@Slf4j
// id对应OrderStateMachineBuilder 里面的 MACHINE_ID,被builder写到.machineId(MACHINEID)里面
@WithStateMachine(id = StateMachineConstant.ORDER_MACHINE_ID)
public class OrderMultipleTransition {

    @OnTransition(target = OrderStatesConstant.UNPAID)
    public void create() {
        log.info("---订单创建，待支付---");
    }

    @OnTransition(source = OrderStatesConstant.UNPAID, target = OrderStatesConstant.WAITING_FOR_SEND)
    public void pay(Message<OrderEvents> message, StateMachine<OrderStates, OrderEvents> stateMachine) {
        try {
            int a = 1 / 0;
            log.info("---订单支付，待发货---");
            log.info("---订单号:{}---", message.getHeaders().get("orderId"));
        } catch (Exception e) {
            // 异常赋值
            stateMachine.setStateMachineError(e);
        }
    }

    @OnTransition(source = OrderStatesConstant.WAITING_FOR_SEND, target = OrderStatesConstant.WAITING_FOR_RECEIVE)
    public void send(Message<OrderEvents> message) {
        log.info("---订单发货，待收货---");
        log.info("---订单号:{}---", message.getHeaders().get("orderId"));
    }

    @OnTransition(source = OrderStatesConstant.WAITING_FOR_RECEIVE, target = OrderStatesConstant.DONE)
    public void receive(Message<OrderEvents> message) {
        log.info("---订单收货，订单完成---");
        log.info("---订单号:{}---", message.getHeaders().get("orderId"));
    }
}
