package com.gpnews.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date publishTime;
    private String userId;
    private String categoryId;
    private Integer status;
}
