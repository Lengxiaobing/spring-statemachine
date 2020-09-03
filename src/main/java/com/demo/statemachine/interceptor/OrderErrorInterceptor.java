package com.demo.statemachine.interceptor;

import com.demo.statemachine.entity.event.OrderEvents;
import com.demo.statemachine.entity.state.OrderStates;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;

/**
 * 文件名: OrderErrorInterceptor.java
 * 作者: zhuxiang
 * 时间: 2020/9/2 20:10
 * 描述: 订单状态机错误拦截器
 */
public class OrderErrorInterceptor extends StateMachineInterceptorAdapter<OrderStates, OrderEvents> {

    @Override
    public Exception stateMachineError(StateMachine<OrderStates, OrderEvents> stateMachine, Exception exception) {
        stateMachine.getExtendedState().getVariables().put("hasError", exception);
        return exception;
    }
}