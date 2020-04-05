package com.gpnews.admin.enums;

/**
 * @author HuangChongHeng
 * @date 2020/4/4
 */
public enum TaskInfoName {


    wy("wy", "1"),
    syncArticle("syncArticle", "2");

    private String name;
    private String id;

    TaskInfoName(String name, String id) {
        this.name = name;
        this.id = id;
    }


    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}