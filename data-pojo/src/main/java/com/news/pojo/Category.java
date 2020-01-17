package com.news.pojo;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
@Data
@Table(name = "a_category")
public class Category extends BaseEntity {
    private String name;
    private Date createdTime;
    private Date modifiedTime;
}
