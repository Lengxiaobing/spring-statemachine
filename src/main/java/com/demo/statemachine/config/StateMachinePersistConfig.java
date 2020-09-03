package com.demo.statemachine.config;

import com.demo.statemachine.entity.StateMachineContextParam;
import com.demo.statemachine.entity.event.FormEvents;
import com.demo.statemachine.entity.event.OrderEvents;
import com.demo.statemachine.entity.state.FormStates;
import com.demo.statemachine.entity.state.OrderStates;
import com.demo.statemachine.persist.FormStateMachinePersist;
import com.demo.statemachine.persist.OrderStateMachinePersist;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

/**
 * 文件名: StateMachinePersistConfig.java
 * 作者: zhuxiang
 * 时间: 2020/8/31 15:13
 * 描述: 状态机持久化配置
 */
@Configuration
public class StateMachinePersistConfig {

    /**
     * 方法名: getPersister
     * 作者/时间: zhuxiang-2020/8/31
     * 描述: 注入StateMachinePersister
     * 参数:
     * 返回: org.springframework.statemachine.persist.StateMachinePersister<com.demo.statemachine.entity.state.OrderStates,com.demo.statemachine.entity.event.OrderEvents,java.lang.String>
     * 异常场景:
     */
    @Bean(name = "orderPersister")
    public StateMachinePersister<OrderStates, OrderEvents, StateMachineContextParam<OrderStates, OrderEvents>> orderPersister(OrderStateMachinePersist orderStateMachinePersist) {
        return new DefaultStateMachinePersister<>(orderStateMachinePersist);
    }

    @Bean(name = "formPersister")
    public StateMachinePersister<FormStates, FormEvents, StateMachineContextParam<FormStates, FormEvents>> formPersister(FormStateMachinePersist formStateMachinePersist) {
        return new DefaultStateMachinePersister<>(formStateMachinePersist);
    }

}
