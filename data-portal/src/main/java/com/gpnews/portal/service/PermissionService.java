package com.gpnews.portal.service;

import com.gpnews.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author HuangChongHeng
 * @date 2019/12/20
 */
public interface PermissionService {

    Set<Permission> queryByRoleId(@Param("id") String id);
}
