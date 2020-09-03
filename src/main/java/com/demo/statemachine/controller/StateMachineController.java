package com.demo.statemachine.controller;

import com.demo.statemachine.config.FormStateMachineBuilderConfig;
import com.demo.statemachine.config.OrderStateMachineBuilderConfig;
import com.demo.statemachine.constant.StateMachineConstant;
import com.demo.statemachine.entity.StateMachineContextParam;
import com.demo.statemachine.entity.event.FormEvents;
import com.demo.statemachine.entity.event.OrderEvents;
import com.demo.statemachine.entity.state.FormStates;
import com.demo.statemachine.entity.state.OrderStates;
import com.demo.statemachine.handler.StateMachineHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件名: StateMachineController.java
 * 作者: zhuxiang
 * 时间: 2020/8/28 16:59
 * 描述: 状态机控制器
 */
@Slf4j
@RestController
@RequestMapping("/statemachine")
public class StateMachineController {

    @Autowired
    private OrderStateMachineBuilderConfig orderStateMachineBuilder;
    @Autowired
    private FormStateMachineBuilderConfig formStateMachineBuilder;

    @Autowired
    private StateMachineHandler stateMachineHandler;

    /**
     * 方法名: testMultipleOrderState
     * 作者/时间: zhuxiang-2020/8/31
     * 描述: 多个订单状态机测试开始
     * 参数:
     * 返回: void
     * 异常场景:
     */
    @PostMapping("/testMultipleOrderState")
    public void testMultipleOrderState() throws Exception {
        StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.orderBuild();
        log.info("==================多个订单状态机测试开始==================");
        log.info("==================stateMachine: " + stateMachine.getId());

        // 创建流程
        stateMachine.start();

        // 触发PAY事件
        stateMachine.sendEvent(OrderEvents.PAY);

        // 触发SEND事件
        stateMachine.sendEvent(OrderEvents.SEND);

        // 触发RECEIVE事件
        stateMachine.sendEvent(OrderEvents.RECEIVE);


        // 获取最终状态
        log.info("---最终状态：" + stateMachine.getState().getId() + "---");
        log.info("==================多个订单状态机测试结束==================");
    }

    /**
     * 方法名: testOrderHandlerState
     * 作者/时间: zhuxiang-2020/9/3
     * 描述: 多个订单持久化状态机
     * 参数: stateId
     * 参数: eventId
     * 参数: orderId
     * 返回: void
     * 异常场景:
     */
    @PostMapping("/testOrderHandlerState")
    public void testOrderHandlerState(@RequestParam("eventId") Integer stateId,
                                      @RequestParam("eventId") Integer eventId,
                                      @RequestParam("orderId") String orderId) throws Exception {
        log.info("==================多个订单持久化状态机测试开始==================");

        // 传递业务参数
        Message<OrderEvents> message = MessageBuilder.withPayload(OrderEvents.getInstance(eventId))
                .setHeader("orderId", orderId).build();
        // 状态机上下文
        StateMachineContextParam<OrderStates, OrderEvents> contextParam = new StateMachineContextParam<>();
        contextParam.setId(StateMachineConstant.ORDER_MACHINE_ID);
        contextParam.setState(OrderStates.getInstance(stateId));
        contextParam.setEvent(OrderEvents.getInstance(eventId));
        stateMachineHandler.orderHandleEventWithState(message, contextParam);

        log.info("==================多个订单持久化状态机测试结束==================");
    }

    /**
     * 方法名: testMultipleFormState
     * 作者/时间: zhuxiang-2020/8/31
     * 描述: 多个表单状态机测试
     * 参数:
     * 返回: void
     * 异常场景:
     */
    @PostMapping("/testMultipleFormState")
    public void testMultipleFormState() throws Exception {
        StateMachine<FormStates, FormEvents> stateMachine = formStateMachineBuilder.formBuild();
        log.info("==================多个表单状态机测试开始==================");
        log.info("==================stateMachine: " + stateMachine.getId());
        // 创建流程
        stateMachine.start();

        // 触发WRITE事件
        stateMachine.sendEvent(FormEvents.WRITE);

        // 触发CONFIRM事件
        stateMachine.sendEvent(FormEvents.CONFIRM);

        // 触发SUBMIT事件
        stateMachine.sendEvent(FormEvents.SUBMIT);

        // 获取最终状态
        log.info("---最终状态：" + stateMachine.getState().getId() + "---");
        log.info("==================多个表单状态机测试结束==================");
    }

    /**
     * 方法名: testFormHandlerState
     * 作者/时间: zhuxiang-2020/9/3
     * 描述: 多个表单持久化状态机
     * 参数: stateId
     * 参数: eventId
     * 参数: formId
     * 返回: void
     * 异常场景:
     */
    @PostMapping("/testFormHandlerState")
    public void testFormHandlerState(@RequestParam("stateId") Integer stateId,
                                     @RequestParam("eventId") Integer eventId,
                                     @RequestParam("formId") String formId) throws Exception {
        log.info("==================多个表单持久化状态机测试开始==================");

        // 传递业务参数
        Message<FormEvents> message = MessageBuilder.withPayload(FormEvents.getInstance(eventId))
                .setHeader("formId", formId).build();
        // 状态机上下文
        StateMachineContextParam<FormStates, FormEvents> contextParam = new StateMachineContextParam<>();
        contextParam.setId(StateMachineConstant.FORM_MACHINE_ID);
        contextParam.setState(FormStates.getInstance(stateId));
        contextParam.setEvent(FormEvents.getInstance(eventId));
        stateMachineHandler.formHandleEventWithState(message, contextParam);

        log.info("==================多个表单持久化状态机测试结束==================");
    }
}
