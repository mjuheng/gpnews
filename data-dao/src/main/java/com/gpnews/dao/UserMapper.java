package com.gpnews.dao;

import com.gpnews.pojo.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * @author HuangChongHeng
 * @date 2019/12/19
 */
public interface UserMapper extends Mapper<User> {

    List<User> pageExclPwd(@Param("user") User user,
                           @Param("start") Integer start,
                           @Param("rows") Integer rows);

    Integer count(@Param("user") User user);
}
