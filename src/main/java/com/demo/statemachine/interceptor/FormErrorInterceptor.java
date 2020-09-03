package com.demo.statemachine.interceptor;

import com.demo.statemachine.entity.event.FormEvents;
import com.demo.statemachine.entity.state.FormStates;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;

/**
 * 文件名: OrderErrorInterceptor.java
 * 作者: zhuxiang
 * 时间: 2020/9/2 20:10
 * 描述: 订单状态机错误拦截器
 */
public class FormErrorInterceptor extends StateMachineInterceptorAdapter<FormStates, FormEvents> {

    @Override
    public Exception stateMachineError(StateMachine<FormStates, FormEvents> stateMachine, Exception exception) {
        stateMachine.getExtendedState().getVariables().put("hasError", exception);
        return exception;
    }
}