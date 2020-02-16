package com.gpnews.pojo;

import lombok.Data;

import javax.persistence.Table;

/**
 * @author HuangChongHeng
 * @date 2020/2/14
 */
@Data
@Table(name = "u_comment")
public class Comment extends BaseEntity{
    private String content;
    private String articleId;
    private String parentId;
    private String userId;
}
