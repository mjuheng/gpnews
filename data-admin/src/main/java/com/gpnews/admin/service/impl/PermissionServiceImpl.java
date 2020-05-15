package com.gpnews.admin.service.impl;

import com.gpnews.admin.service.PermissionService;
import com.gpnews.admin.service.RoleService;
import com.gpnews.admin.service.UserService;
import com.gpnews.dao.PermissionMapper;
import com.gpnews.pojo.Permission;
import com.gpnews.pojo.Role;
import com.gpnews.pojo.User;
import com.gpnews.pojo.vo.PermissionVo;
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
    @Resource
    private UserService userServiceImpl;

    @Override
    public Mapper<Permission> getMapper() {
        return permissionMapper;
    }

    @Override
    public Set<Permission> queryByRoleId(String id) {
        // 获取数据库的全部权限
//        List<Permission> allPerms = permissionMapper.selectAll();
//        Map<String, List<Permission>> permParentMap = listToMap(allPerms);      // key值为parentId

//        Set<Permission> permissions = new HashSet<>();  // 用户拥有的权限
        String permId = roleServiceImpl.load(id).getPermId();
        List<Permission> permList = new ArrayList<>();
        if (permId != null) {
            String[] permIds = permId.split(";");
            permList = permissionMapper.selectByIds(permIds);
        }
//        permissions.addAll(permList);
//        for (Permission perm : permList) {
//            List<Permission> children = new ArrayList<>();
//            getChildren(perm, permParentMap, children);
//            permissions.addAll(children);
//        }
//        return permissions;
        return new HashSet<>(permList);
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

    @Override
    public List<PermissionVo> queryTree() {
        List<PermissionVo> ret  = new ArrayList<>();
        List<PermissionVo> parent = new ArrayList<>();
        List<Permission> permList = permissionMapper.selectAll();
        Map<String, List<Permission>> permParentMap = listToMap(permList);
        for (Permission perm: permList){
            if (StringUtils.isBlank(perm.getParentId())){
                parent.add(new PermissionVo(perm));
            }
        }
        for (PermissionVo permVo: parent){
            getChildrenTree(permVo, permParentMap);
            ret.add(permVo);
        }
        return ret;
    }

    private void getChildrenTree(PermissionVo permVo, Map<String, List<Permission>> permParentMap){
        List<Permission> list = permParentMap.get(permVo.getId());
        if (list != null && list.size() > 0) {
            permVo.setChildren(new ArrayList<>());
            for (Permission perm : list) {
                PermissionVo vo = new PermissionVo(perm);
                permVo.getChildren().add(vo);
                getChildrenTree(vo, permParentMap);
            }
        }
    }

    @Override
    public List<PermissionVo> getTreeByUser(String id) {
        Set<Permission> currUserPerm = new HashSet<>();
        Set<Role> roles = roleServiceImpl.queryByUserId(id);
        for (Role role: roles){
            currUserPerm.addAll(this.queryByRoleId(role.getId()));
        }

        List<PermissionVo> ret  = new ArrayList<>();
        List<PermissionVo> parent = new ArrayList<>();
        List<Permission> permList = new ArrayList<>(currUserPerm);
        permList.sort((o1, o2) -> {
            boolean b = o1.getCreatedTime().getTime() > o2.getCreatedTime().getTime();
            return b?1:-1;
        });
        Map<String, List<Permission>> permParentMap = listToMap(permList);
        for (Permission perm: permList){
            if (StringUtils.isBlank(perm.getParentId())){
                parent.add(new PermissionVo(perm));
            }
        }
        for (PermissionVo permVo: parent){
            getChildrenTree(permVo, permParentMap);
            ret.add(permVo);
        }
        return ret;
    }
}
