package com.gpnews.admin.enums;

/**
 * @author HuangChongHeng
 * @date 2020/3/20
 */
public enum  DynamicType {

    favorites(0, "收藏"),
    transmit(1, "转发"),
    comment(2, "评论");

    private Integer code;
    private String name;

    DynamicType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
