package com.gpnews.pojo.vo;

import com.gpnews.pojo.Role;
import lombok.Data;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/2/6
 */
@Data
public class RoleVo extends Role {
    List<RoleVo> children;

    public RoleVo(){}

    public RoleVo(Role role){
        this.setId(role.getId());
        this.setName(role.getName());
        this.setParentId(role.getParentId());
        this.setDescription(role.getDescription());
        this.setPermId(role.getPermId());
    }
}
