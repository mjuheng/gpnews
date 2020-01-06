package com.news.utils.result;

/**
 * @author HuangChongHeng
 * @date 2019/12/31
 */
public class SingleResult<T> extends CommonResult{
    private static final long serialVersionUID = 1L;
    private T data;

    public SingleResult() {
    }

    public SingleResult(String code, String message) {
        super(code, message);
    }

    public SingleResult(String code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
