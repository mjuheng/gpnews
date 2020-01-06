package com.news.portal.service.impl;

import com.news.dao.PermissionMapper;
import com.news.pojo.Permission;
import com.news.portal.service.PermissionService;
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
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public Mapper<Permission> getMapper() {
        return permissionMapper;
    }

    @Override
    public Set<Permission> queryByRoleId(String id) {
        Set<Permission> permissions = new HashSet<>();
        List<Permission> list = permissionMapper.queryByRoleId(id);
        for (Permission permission: list){
            if (permissions.contains(permission)){
                break;
            }
            permissions.add(permission);
            // 添加父权限
            while (permission.getParentId() != null){
                permission = load(permission.getParentId());
                if (permissions.contains(permission)){
                    break;
                }
                permissions.add(permission);
            }

        }
        return permissions;
    }
}
