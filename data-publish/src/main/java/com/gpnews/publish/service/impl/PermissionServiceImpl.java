package com.gpnews.publish.service.impl;

import com.gpnews.dao.PermissionMapper;
import com.gpnews.pojo.Permission;
import com.gpnews.pojo.Role;
import com.gpnews.pojo.vo.PermissionVo;
import com.gpnews.publish.service.PermissionService;
import com.gpnews.publish.service.RoleService;
import com.gpnews.publish.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author HuangChongHeng
 * @date 2019/12/20
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RoleService roleServiceImpl;

    @Override
    public Mapper<Permission> getMapper() {
        return permissionMapper;
    }

    @Override
    public Set<Permission> queryByRoleId(String id) {
        // 获取数据库的全部权限
        List<Permission> allPerms = permissionMapper.selectAll();
        Map<String, List<Permission>> permParentMap = listToMap(allPerms);      // key值为parentId

        Set<Permission> permissions = new HashSet<>();  // 用户拥有的权限
        String permId = roleServiceImpl.load(id).getPermId();
        List<Permission> permList = new ArrayList<>();
        if (permId != null) {
            String[] permIds = permId.split(";");
            permList = permissionMapper.selectByIds(permIds);
        }
        permissions.addAll(permList);
        for (Permission perm : permList) {
            List<Permission> children = new ArrayList<>();
            getChildren(perm, permParentMap, children);
            permissions.addAll(children);
        }
        return permissions;
    }

    /**
     * 获取所有子权限
     * @param permission
     * @param permParentMap
     * @param result
     */
    private void getChildren(Permission permission, Map<String, List<Permission>> permParentMap, List<Permission> result){
        List<Permission> list = permParentMap.get(permission.getId());
        if (list == null || list.size() <= 0)
            return;
        for (Permission perm: list){
            result.add(perm);
            getChildren(perm, permParentMap, result);
        }
    }

    private Map<String, List<Permission>> listToMap(List<Permission> permList){
        Map<String, List<Permission>> map = new HashMap<>();
        for (Permission permission: permList){
            List<Permission> perms = map.get(permission.getParentId());
            if (perms != null && perms.size() > 0){
                perms.add(permission);
            }else {
                List<Permission> list = new ArrayList<>();
                list.add(permission);
                map.put(permission.getParentId(), list);
            }
        }
        return map;
    }

}
