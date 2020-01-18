package com.gpnews.pojo;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author HuangChongHeng
 * @date 2019/12/19
 */
@Table(name = "u_permission")
@Data
public class Permission extends BaseEntity{
    private String name;
    private String parentId;
    private String description;
    private Date createdTime;
    private Date modifiedTime;
}
