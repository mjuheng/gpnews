package com.gpnews.publish.service.impl;

import com.gpnews.publish.service.RoleService;
import com.gpnews.dao.RoleMapper;
import com.gpnews.pojo.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author HuangChongHeng
 * @date 2019/12/20
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Override
    public Mapper<Role> getMapper() {
        return roleMapper;
    }
    @Resource
    private RoleMapper roleMapper;
    @Override
    public Set<Role> queryByUserId(String id) {
        Set<Role> roles = new HashSet<>();
        List<Role> list = roleMapper.queryByUserId(id);
        for (Role role: list){
            if (roles.contains(role)){
                break;
            }
            roles.add(role);
            // 添加父角色
            while (role != null && !StringUtils.isBlank(role.getParentId())){
                role = load(role.getParentId());
                if (roles.contains(role)){
                    break;
                }
                roles.add(role);
            }
        }
        return roles;
    }
}
