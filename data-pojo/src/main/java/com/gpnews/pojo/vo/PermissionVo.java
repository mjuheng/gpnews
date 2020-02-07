package com.gpnews.pojo.vo;

import com.gpnews.pojo.Permission;
import lombok.Data;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/2/3
 */
@Data
public class PermissionVo extends Permission {
    List<PermissionVo> children;

    public PermissionVo(){}
    public PermissionVo(Permission perm){
        this.setId(perm.getId());
        this.setName(perm.getName());
        this.setCName(perm.getCName());
        this.setParentId(perm.getParentId());
        this.setDescription(perm.getDescription());
        this.setCreatedTime(perm.getCreatedTime());
        this.setModifiedTime(perm.getModifiedTime());
    }
}
