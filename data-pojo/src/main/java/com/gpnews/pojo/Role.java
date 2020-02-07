package com.gpnews.pojo;

import lombok.Data;
import org.springframework.context.annotation.Primary;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "角色名不能为空")
    private String name;
    private String parentId;
    private String description;
    private String permId;

    @Transient
    private Permission[] permission;
}
