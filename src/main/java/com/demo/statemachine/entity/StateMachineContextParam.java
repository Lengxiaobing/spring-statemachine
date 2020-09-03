package com.demo.statemachine.entity;

import lombok.Data;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachineContext;

import java.util.List;
import java.util.Map;

/**
 * 文件名: StateMachinePersist.java
 * 作者: zhuxiang
 * 时间: 2020/9/3 11:23
 * 描述: 状态机持久化实体
 */
@Data
public class StateMachineContextParam<S, E> {
    /**
     * 状态机ID
     */
    private String id;
    private List<StateMachineContext<S, E>> childs;
    private List<String> childRefs;
    /**
     * 状态
     */
    private S state;
    private Map<S, S> historyStates;
    /**
     * 事件
     */
    private E event;
    private Map<String, Object> eventHeaders;
    private ExtendedState extendedState;
}
