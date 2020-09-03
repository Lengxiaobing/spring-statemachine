package com.demo.statemachine.entity.state;

/**
 * 文件名: FormStates.java
 * 作者: zhuxiang
 * 时间: 2020/8/28 16:16
 * 描述: 表单状态
 */
public enum FormStates {
    /**
     * 表单状态
     */
    BLANK_FORM(1, "BLANK_FORM", "空白表单"),
    FULL_FORM(2, "FULL_FORM", "填写完成"),
    CONFIRM_FORM(3, "CONFIRM_FORM", "校验表单"),
    SUCCESS_FORM(4, "SUCCESS_FORM", "成功表单");

    FormStates(Integer code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    private Integer code;
    private String msg;
    private String desc;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static FormStates getInstance(Integer value) {
        FormStates[] fromStates = values();
        for (FormStates states : fromStates) {
            if (states.getCode().equals(value)) {
                return states;
            }
        }
        return null;
    }
}
