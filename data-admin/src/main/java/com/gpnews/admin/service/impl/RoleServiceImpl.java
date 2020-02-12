package com.gpnews.admin.service.impl;

import com.gpnews.admin.service.RoleService;
import com.gpnews.admin.service.UserService;
import com.gpnews.dao.RoleMapper;
import com.gpnews.pojo.Permission;
import com.gpnews.pojo.Role;
import com.gpnews.pojo.vo.PermissionVo;
import com.gpnews.pojo.vo.RoleVo;
import com.gpnews.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
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

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserService userServiceImpl;

    @Override
    public Set<Role> queryByUserId(String id) {
        // 获取数据库的全部权限
        List<Role> allRoles = roleMapper.selectAll();
        Map<String, List<Role>> roleParentMap = listToMap(allRoles);      // key值为parentId

        Set<Role> roles = new HashSet<>();  // 用户拥有的角色
        String roleId = userServiceImpl.load(id).getRoleId();
        List<Role> roleList = new ArrayList<>();
        if (roleId != null){
            String[] roleIds = roleId.split(";");
            roleList = roleMapper.selectByIds(roleIds);
        }
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

    @Override
    public List<Role> page(Role role, Integer currPage, Integer rows) {
        int start = PageUtil.getStart(currPage, rows);
        return roleMapper.page(role, start, rows);
    }

    @Override
    public Integer count(Role role) {
        return roleMapper.count(role);
    }

    @Override
    public List<RoleVo> queryTree(){
        List<RoleVo> ret  = new ArrayList<>();
        List<RoleVo> parent = new ArrayList<>();
        List<Role> roleList = roleMapper.selectAll();
        Map<String, List<Role>> roleParentMap = listToMap(roleList);
        for (Role role: roleList){
            if (StringUtils.isBlank(role.getParentId())){
                parent.add(new RoleVo(role));
            }
        }
        for (RoleVo roleVo: parent){
            getChildrenTree(roleVo, roleParentMap);
            ret.add(roleVo);
        }
        return ret;
    }

    private void getChildrenTree(RoleVo roleVo, Map<String, List<Role>> roleParentMap){
        List<Role> list = roleParentMap.get(roleVo.getId());
        if (list != null && list.size() > 0) {
            roleVo.setChildren(new ArrayList<>());
            for (Role role : list) {
                RoleVo vo = new RoleVo(role);
                roleVo.getChildren().add(vo);
                getChildrenTree(vo, roleParentMap);
            }
        }
    }

    @Override
    public List<Role> selectByIds(String[] id) {
        return roleMapper.selectByIds(id);
    }

    @Override
    public Mapper<Role> getMapper() {
        return roleMapper;
    }
}
