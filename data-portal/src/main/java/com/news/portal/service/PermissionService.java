package com.news.portal.service;

import com.news.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author HuangChongHeng
 * @date 2019/12/20
 */
public interface PermissionService {

    Set<Permission> queryByRoleId(@Param("id") String id);
}
