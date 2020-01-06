package com.news.utils.result;

public enum TemplateBootResultCodeEnum {
    SUCCESS("0", "操作成功"),
    ERROR("500", "操作失败"),
    NOLOGIN("401", "用户未登录"),;
    private String code;
    private String message;

    private TemplateBootResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
