package com.demo.statemachine.transition;

import com.demo.statemachine.constant.FormStatesConstant;
import com.demo.statemachine.constant.StateMachineConstant;
import com.demo.statemachine.entity.event.FormEvents;
import com.demo.statemachine.entity.state.FormStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * 文件名: FormMultipleTransition.java
 * 作者: zhuxiang
 * 时间: 2020/8/28 17:38
 * 描述: 多流程表单状态机监听器
 */
@Slf4j
// id对应OrderStateMachineBuilder 里面的 MACHINE_ID,被builder写到.machineId(MACHINEID)里面
@WithStateMachine(id = StateMachineConstant.FORM_MACHINE_ID)
public class FormMultipleTransition {

    @OnTransition(target = FormStatesConstant.BLANK_FORM)
    public void create() {
        log.info("---表单创建，待填写---");
    }

    @OnTransition(source = FormStatesConstant.BLANK_FORM, target = FormStatesConstant.FULL_FORM)
    public void write(Message<FormEvents> message, StateMachine<FormStates, FormEvents> stateMachine) {
        try {
            int a = 1 / 0;
            log.info("---填写完成，待校验---");
            log.info("---表单号:{}---", message.getHeaders().get("formId"));
        } catch (Exception e) {
            // 异常赋值
            stateMachine.setStateMachineError(e);
        }
    }

    @OnTransition(source = FormStatesConstant.FULL_FORM, target = FormStatesConstant.CONFIRM_FORM)
    public void confirm() {
        log.info("---校验完成，待提交---");
    }

    @OnTransition(source = FormStatesConstant.CONFIRM_FORM, target = FormStatesConstant.SUCCESS_FORM)
    public void submit() {
        log.info("---提交完成，表单完成---");
    }
}
