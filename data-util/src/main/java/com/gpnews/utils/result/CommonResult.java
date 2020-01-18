package com.gpnews.utils.result;

/**
 * @author HuangChongHeng
 * @date 2019/12/31
 */
public class CommonResult {
    private static final long serialVersionUID = 1L;
    protected static final String SUCCESS_CODE;
    protected static final String SUCCESS_MESSAGE;
    private String code;
    private String message;

    public CommonResult() {
        this.code = SUCCESS_CODE;
        this.message = SUCCESS_MESSAGE;
    }

    public CommonResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return null != this.code && SUCCESS_CODE.equals(this.code);
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

    static {
        SUCCESS_CODE = TemplateBootResultCodeEnum.SUCCESS.getCode();
        SUCCESS_MESSAGE = TemplateBootResultCodeEnum.SUCCESS.getMessage();
    }
}
