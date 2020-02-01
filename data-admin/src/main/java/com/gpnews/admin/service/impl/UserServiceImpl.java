package com.gpnews.admin.service.impl;

import com.gpnews.admin.service.UserService;
import com.gpnews.dao.UserMapper;
import com.gpnews.pojo.User;
import com.gpnews.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

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

    @Override
    public List<User> pageExclPwd(User user, Integer currPage, Integer rows) {
        int start = PageUtil.getStart(currPage, rows);
        return userMapper.pageExclPwd(user, start, rows);
    }

    @Override
    public Integer count(User user) {
        Integer count = userMapper.count(user);
        return count;
    }
}
