package com.gpnews.portal.service.impl;

import com.gpnews.dao.UserMapper;
import com.gpnews.pojo.User;
import com.gpnews.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author HuangChongHeng
 * @date 2019/12/19
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Mapper<User> getMapper() {
        return userMapper;
    }

}
