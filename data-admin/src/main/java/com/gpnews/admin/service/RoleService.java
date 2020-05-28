package com.gpnews.admin.service;

import com.gpnews.pojo.Role;
import com.gpnews.pojo.vo.RoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author HuangChongHeng
 * @date 2019/12/20
 */
public interface RoleService extends BaseService<Role> {
    Set<Role> queryByUserId(String id);

    List<Role> page(Role role, Integer currPage, Integer rows);

    Integer count(Role role);

    List<RoleVo> queryTree();

    List<Role> selectByIds(String[] id);

    void insertRoleUser(Map<String, Object> map);

    void delByUserId(String userId);

    List<Map<String, Object>> getRoleByUserId(String userId);

    Integer updateWithParent(Role role);
}
