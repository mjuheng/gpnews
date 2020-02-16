package com.gpnews.consumer.service;

import com.gpnews.pojo.Role;
import com.gpnews.pojo.vo.RoleVo;

import java.util.List;
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
}
