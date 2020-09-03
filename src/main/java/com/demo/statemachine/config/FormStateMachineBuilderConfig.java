package com.demo.statemachine.config;

import com.demo.statemachine.constant.StateMachineConstant;
import com.demo.statemachine.entity.event.FormEvents;
import com.demo.statemachine.entity.state.FormStates;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

import java.util.EnumSet;

/**
 * 文件名: FormStateMachineBuilder.java
 * 作者: zhuxiang
 * 时间: 2020/8/31 10:42
 * 描述: 多个表单状态机构建
 */
@Configuration
@EnableStateMachine
public class FormStateMachineBuilderConfig {
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
    public StateMachine<FormStates, FormEvents> formBuild() throws Exception {
        StateMachineBuilder.Builder<FormStates, FormEvents> builder = StateMachineBuilder.builder();
        //状态机配置配置器
        builder.configureConfiguration()
                .withConfiguration()
                .machineId(StateMachineConstant.FORM_MACHINE_ID)
                .beanFactory(beanFactory);
        //状态机状态配置器
        builder.configureStates()
                .withStates()
                .initial(FormStates.BLANK_FORM)
                .states(EnumSet.allOf(FormStates.class));
        //状态机转换配置器
        builder.configureTransitions()
                .withExternal()
                // 原状态
                .source(FormStates.BLANK_FORM)
                // 目标状态
                .target(FormStates.FULL_FORM)
                // 状态事件
                .event(FormEvents.WRITE)

                .and()
                .withExternal()
                .source(FormStates.FULL_FORM)
                .target(FormStates.CONFIRM_FORM)
                .event(FormEvents.CONFIRM)

                .and()
                .withExternal()
                .source(FormStates.CONFIRM_FORM)
                .target(FormStates.SUCCESS_FORM)
                .event(FormEvents.SUBMIT);
        return builder.build();
    }

}
