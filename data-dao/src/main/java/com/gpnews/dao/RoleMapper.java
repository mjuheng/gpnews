package com.gpnews.dao;

import com.gpnews.pojo.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2019/12/20
 */
public interface RoleMapper extends Mapper<Role> {
    List<Role> queryByUserId(@Param("id") String id);

    List<Role> page(@Param("role") Role role,
                    @Param("start") Integer start,
                    @Param("rows") Integer rows);

    Integer count(@Param("role") Role role);

    List<Role> selectByIds(@Param("ids") String[] ids);
}
