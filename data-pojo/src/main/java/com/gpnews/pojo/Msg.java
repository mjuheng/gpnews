package com.gpnews.pojo;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author HuangChongHeng
 * @date 2020/4/1
 */
@Table(name = "u_msg")
@Data
public class Msg extends BaseEntity{
    @NotNull(message = "标题不能为空")
    private String title;
    private String userId;
    private String optUserId;
    @NotNull(message = "内容不能为空")
    private String content;
    private Integer type;
    private Boolean isRead;

    public Msg(){};

    public Msg(String id, Boolean isRead){
        super.setId(id);
        this.isRead = isRead;
    }

    public Msg(@NotNull(message = "标题不能为空") String title, String userId, String optUserId, @NotNull(message = "内容不能为空") String content, Integer type, Boolean isRead) {
        this.title = title;
        this.userId = userId;
        this.optUserId = optUserId;
        this.content = content;
        this.type = type;
        this.isRead = isRead;
    }

    public Msg(String title, String userId, String content, Integer type, Boolean isRead){
        this.title = title;
        this.userId = userId;
        this.content = content;
        this.type = type;
        this.isRead = isRead;
    }
}
