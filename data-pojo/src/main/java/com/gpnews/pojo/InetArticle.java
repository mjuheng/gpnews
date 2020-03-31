package com.gpnews.pojo;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author HuangChongHeng
 * @date 2020/3/29
 */
@Table(name = "inet_article")
@Data
public class InetArticle extends BaseEntity{
    private String username;
    private String title;
    private Date publishTime;
    private String content;
    private Integer type;
    private String url;
}
