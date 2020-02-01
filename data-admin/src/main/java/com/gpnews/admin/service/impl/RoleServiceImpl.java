package com.gpnews.admin.service.impl;

import com.gpnews.admin.service.RoleService;
import com.gpnews.dao.RoleMapper;
import com.gpnews.pojo.Role;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.*;

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
        // 获取数据库的全部权限
        List<Role> allRoles = roleMapper.selectAll();
        Map<String, List<Role>> roleParentMap = listToMap(allRoles);      // key值为parentId

        Set<Role> roles = new HashSet<>();  // 用户拥有的角色
        List<Role> roleList = roleMapper.queryByUserId(id);
        roles.addAll(roleList);
        for (Role r : roleList) {
            List<Role> children = new ArrayList<>();
            getChildren(r, roleParentMap, children);
            roles.addAll(children);
        }
        return roles;
    }

    /**
     * 获取所有子角色
     * @param role
     * @param roleParentMap
     * @param result
     */
    private void getChildren(Role role, Map<String, List<Role>> roleParentMap, List<Role> result){
        List<Role> list = roleParentMap.get(role.getId());
        if (list == null || list.size() <= 0)
            return;
        for (Role r: list){
            result.add(r);
            getChildren(r, roleParentMap, result);
        }
    }

    private Map<String, List<Role>> listToMap(List<Role> roleList){
        Map<String, List<Role>> map = new HashMap<>();
        for (Role r: roleList){
            List<Role> perms = map.get(r.getParentId());
            if (perms != null && perms.size() > 0){
                perms.add(r);
            }else {
                List<Role> list = new ArrayList<>();
                list.add(r);
                map.put(r.getParentId(), list);
            }
        }
        return map;
    }
}
