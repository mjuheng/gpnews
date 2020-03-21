package com.gpnews.pojo;

import lombok.Data;

import javax.persistence.Table;

/**
 * @author HuangChongHeng
 * @date 2020/3/20
 */
@Data
@Table(name = "a_dynamic")
public class Dynamic extends BaseEntity{
    private Integer type;
    private String articleId;
    private String userId;
    private String optUserId;
}
