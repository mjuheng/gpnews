package com.news.pojo;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
@Table(name = "a_article")
@Data
public class Article extends BaseEntity {
    private String title;
    private String headImage;
    private String content;
    private Date publishTime;
    private String userId;
    private String categoryId;
    private Integer status;
}
