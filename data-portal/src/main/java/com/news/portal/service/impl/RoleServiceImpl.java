package com.news.portal.service.impl;

import com.news.dao.RoleMapper;
import com.news.pojo.Role;
import com.news.portal.service.RoleService;
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
            while (role.getParentId() != null){
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
