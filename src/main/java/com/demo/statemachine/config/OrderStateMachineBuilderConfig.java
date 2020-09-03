package com.demo.statemachine.config;

import com.demo.statemachine.constant.StateMachineConstant;
import com.demo.statemachine.entity.event.OrderEvents;
import com.demo.statemachine.entity.state.OrderStates;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

import java.util.EnumSet;

/**
 * 文件名: OrderStateMachineBuilder.java
 * 作者: zhuxiang
 * 时间: 2020/8/31 10:42
 * 描述: 多个订单状态机构建
 */
@Configuration
@EnableStateMachine
public class OrderStateMachineBuilderConfig {
    @Autowired
    private BeanFactory beanFactory;

    /**
     * 方法名: build
     * 作者/时间: zhuxiang-2020/8/31
     * 描述: 构建状态机
     * 参数: beanFactory
     * 返回: org.springframework.statemachine.StateMachine<com.demo.statemachine.entity.state.OrderStates,com.demo.statemachine.entity.event.OrderEvents>
     * 异常场景:
     */
    @Bean
    public StateMachine<OrderStates, OrderEvents> orderBuild() throws Exception {
        StateMachineBuilder.Builder<OrderStates, OrderEvents> builder = StateMachineBuilder.builder();
        //状态机配置配置器
        builder.configureConfiguration()
                .withConfiguration()
                .machineId(StateMachineConstant.ORDER_MACHINE_ID)
                .beanFactory(beanFactory);
        //状态机状态配置器
        builder.configureStates()
                .withStates()
                .initial(OrderStates.UNPAID)
                .states(EnumSet.allOf(OrderStates.class));
        //状态机转换配置器
        builder.configureTransitions()
                .withExternal()
                // 原状态
                .source(OrderStates.UNPAID)
                // 目标状态
                .target(OrderStates.WAITING_FOR_SEND)
                // 状态事件
                .event(OrderEvents.PAY)

                .and()
                .withExternal()
                .source(OrderStates.WAITING_FOR_SEND)
                .target(OrderStates.WAITING_FOR_RECEIVE)
                .event(OrderEvents.SEND)

                .and()
                .withExternal()
                .source(OrderStates.WAITING_FOR_RECEIVE)
                .target(OrderStates.DONE)
                .event(OrderEvents.RECEIVE);
        return builder.build();
    }
}
