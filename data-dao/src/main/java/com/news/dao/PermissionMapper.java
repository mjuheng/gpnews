package com.news.dao;

import com.news.pojo.Permission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PermissionMapper extends Mapper<Permission> {

    List<Permission> queryByRoleId(@Param("id") String id);
}
