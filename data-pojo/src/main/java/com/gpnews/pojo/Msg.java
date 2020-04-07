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

}
