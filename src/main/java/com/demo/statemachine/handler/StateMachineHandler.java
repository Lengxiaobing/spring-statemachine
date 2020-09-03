package com.demo.statemachine.handler;

import com.demo.statemachine.config.FormStateMachineBuilderConfig;
import com.demo.statemachine.config.OrderStateMachineBuilderConfig;
import com.demo.statemachine.entity.StateMachineContextParam;
import com.demo.statemachine.entity.event.FormEvents;
import com.demo.statemachine.entity.event.OrderEvents;
import com.demo.statemachine.entity.state.FormStates;
import com.demo.statemachine.entity.state.OrderStates;
import com.demo.statemachine.interceptor.FormErrorInterceptor;
import com.demo.statemachine.interceptor.OrderErrorInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.support.LifecycleObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * 文件名: StateMachineHandler.java
 * 作者: zhuxiang
 * 时间: 2020/9/3 16:13
 * 描述: 状态机实例
 */
@Slf4j
@Component
public class StateMachineHandler extends LifecycleObjectSupport {

    @Resource(name = "orderPersister")
    private StateMachinePersister<OrderStates, OrderEvents, StateMachineContextParam<OrderStates, OrderEvents>> orderMemoryPersister;
    @Resource(name = "formPersister")
    private StateMachinePersister<FormStates, FormEvents, StateMachineContextParam<FormStates, FormEvents>> fromMemoryPersister;

    private StateMachine<OrderStates, OrderEvents> orderStateMachine;
    private StateMachine<FormStates, FormEvents> fromOrderStateMachine;

    /**
     * 实例化新的持久化状态机Handler
     *
     * @param orderBuilder 状态机实例
     * @param formBuilder  状态机实例
     */
    public StateMachineHandler(OrderStateMachineBuilderConfig orderBuilder, FormStateMachineBuilderConfig formBuilder) throws Exception {
        Assert.notNull(orderBuilder, "State machine must be set");
        Assert.notNull(formBuilder, "State machine must be set");
        this.orderStateMachine = orderBuilder.orderBuild();
        this.fromOrderStateMachine = formBuilder.formBuild();
    }

    /**
     * 方法名: onInit
     * 作者/时间: zhuxiang-2020/9/3
     * 描述: 初始化拦截器
     * 参数:
     * 返回: void
     * 异常场景:
     */
    @Override
    protected void onInit() throws Exception {
        orderStateMachine.getStateMachineAccessor()
                .doWithAllRegions(function -> {
                    function.addStateMachineInterceptor(new OrderErrorInterceptor());
                });
        fromOrderStateMachine.getStateMachineAccessor()
                .doWithAllRegions(function -> {
                    function.addStateMachineInterceptor(new FormErrorInterceptor());
                });
    }

    /**
     * 处理entity的事件
     *
     * @param event
     * @param context
     * @return 如果事件被接受处理，返回true
     */
    public boolean orderHandleEventWithState(Message<OrderEvents> event, StateMachineContextParam<OrderStates, OrderEvents> context) throws Exception {
        orderStateMachine = orderMemoryPersister.restore(orderStateMachine, context);
        orderStateMachine.sendEvent(event);
        // 判断是否有异常
        if (orderStateMachine.hasStateMachineError()) {
            orderStateMachine.setStateMachineError(null);
            // 获取异常信息
            Object hasError = orderStateMachine.getExtendedState().getVariables().get("hasError");
            // 清理异常信息- 扩展字段的异常信息会一直存在, 所以处理完毕之后,需要清除异常信息
            orderStateMachine.getExtendedState().getVariables().remove("hasError");
            throw new Exception(hasError.toString());
        }
        // 判断扩展字段是否有异常信息
        if (orderStateMachine.getExtendedState().getVariables().containsKey("hasError")) {
            orderStateMachine.setStateMachineError(null);
        }
        return orderStateMachine.hasStateMachineError();
    }

    /**
     * 处理entity的事件
     *
     * @param event
     * @param context
     * @return 如果事件被接受处理，返回true
     */
    public boolean formHandleEventWithState(Message<FormEvents> event, StateMachineContextParam<FormStates, FormEvents> context) throws Exception {
        fromOrderStateMachine = fromMemoryPersister.restore(fromOrderStateMachine, context);
        fromOrderStateMachine.sendEvent(event);
        if (fromOrderStateMachine.hasStateMachineError()) {
            fromOrderStateMachine.setStateMachineError(null);
            Object hasError = fromOrderStateMachine.getExtendedState().getVariables().get("hasError");
            fromOrderStateMachine.getExtendedState().getVariables().remove("hasError");
            throw new Exception(hasError.toString());
        }
        return fromOrderStateMachine.hasStateMachineError();
    }

}