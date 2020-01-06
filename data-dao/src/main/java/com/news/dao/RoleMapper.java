package com.news.dao;

import com.news.pojo.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2019/12/20
 */
public interface RoleMapper extends Mapper<Role> {
    List<Role> queryByUserId(@Param("id") String id);
}
