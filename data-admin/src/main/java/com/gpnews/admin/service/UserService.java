package com.gpnews.admin.service;

import com.gpnews.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author HuangChongHeng
 * @date 2019/12/19
 */
public interface UserService extends BaseService<User> {
    List<User> pageExclPwd(User user, Integer currPage, Integer rows);

    Integer count(User user);

}
