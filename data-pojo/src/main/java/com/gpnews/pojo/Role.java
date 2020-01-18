package com.gpnews.pojo;

import lombok.Data;
import org.springframework.context.annotation.Primary;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author HuangChongHeng
 * @date 2019/12/19
 */
@Data
@Table(name = "u_role")
public class Role extends BaseEntity{
    @Id
    private String id;
    private String name;
    private String parentId;
    private String description;
    private Date createdTime;
    private Date modifiedTime;

    @Transient
    private Permission[] permission;
}
