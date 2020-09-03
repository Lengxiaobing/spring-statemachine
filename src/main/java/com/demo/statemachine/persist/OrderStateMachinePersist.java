package com.demo.statemachine.persist;

import com.demo.statemachine.entity.StateMachineContextParam;
import com.demo.statemachine.entity.event.OrderEvents;
import com.demo.statemachine.entity.state.OrderStates;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

/**
 * 文件名: InMemoryStateMachinePersist.java
 * 作者: zhuxiang
 * 时间: 2020/8/31 15:10
 * 描述: 状态机持久化 -- 内存模式
 */
@Component
public class OrderStateMachinePersist implements StateMachinePersist<OrderStates, OrderEvents, StateMachineContextParam<OrderStates, OrderEvents>> {

    @Override
    public void write(StateMachineContext<OrderStates, OrderEvents> context, StateMachineContextParam<OrderStates, OrderEvents> contextObj) throws Exception {
        // 不做任何处理
    }

    @Override
    public StateMachineContext<OrderStates, OrderEvents> read(StateMachineContextParam<OrderStates, OrderEvents> contextObj) throws Exception {
        // 获取持久化的状态
        return new DefaultStateMachineContext<>(
                contextObj.getState(),
                contextObj.getEvent(),
                contextObj.getEventHeaders(),
                contextObj.getExtendedState(),
                contextObj.getHistoryStates(),
                contextObj.getId());
    }
}
