package com.news.portal.service;

import com.news.pojo.Role;

import java.util.List;
import java.util.Set;

/**
 * @author HuangChongHeng
 * @date 2019/12/20
 */
public interface RoleService extends BaseService<Role> {
    Set<Role> queryByUserId(String id);
}
