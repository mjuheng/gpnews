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
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        // 获取数据库的全部角色
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
    public void insertRoleUser(Map<String, Object> map) {
        roleMapper.insertRoleUser(map);
    }

    @Override
    public void delByUserId(String userId) {
        roleMapper.delByUserId(userId);
    }

    @Override
    public List<Map<String, Object>> getRoleByUserId(String userId) {
        return roleMapper.getRoleByUserId(userId);
    }

    @Override
    public Integer updateWithParent(Role role) {
        if (!StringUtils.isBlank(role.getParentId())){
            // 判断父角色是否为自己
            if (Objects.equals(role.getParentId(), role.getId())){
                return 1;
            }
            // 同步其他角色的依赖信息
            Role oldRole = load(role.getId());
            Example roleExample = new Example(role.getClass());
            roleExample.createCriteria().andEqualTo("parentId", role.getId());
            List<Role> children = roleMapper.selectByExample(roleExample);
            for (Role child : children) {
                child.setParentId(oldRole.getParentId());
            }
            updateBatch(children);
        }
        update(role);
        return 0;
    }

    @Override
    public Mapper<Role> getMapper() {
        return roleMapper;
    }
}
