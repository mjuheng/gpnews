package com.gpnews.admin.enums;

/**
 * @author HuangChongHeng
 * @date 2020/1/20
 */
public enum ArticleStatus {
    draft(0, "草稿"),
    approval(1, "待审核"),
    agree(2, "审核通过"),
    refuse(3, "审核失败");

    private Integer code;
    private String name;

    ArticleStatus(Integer code, String name) {
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
