package com.gpnews.consumer.service.impl;

import com.gpnews.consumer.service.UserService;
import com.gpnews.dao.UserMapper;
import com.gpnews.pojo.User;
import com.gpnews.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public String checkUserInfo(User user) {
        String msg = "";
        User u = new User();
        u.setUsername(user.getUsername());
        User entity = userMapper.selectOne(u);
        if (entity != null && !entity.getId().equals(user.getId())){
            msg += "、用户名";
        }
        if (user.getPhone() != null){
            u = new User();
            u.setPhone(user.getPhone());
            entity = userMapper.selectOne(u);
            if (entity != null && !entity.getId().equals(user.getId())){
                msg += "、手机号";
            }
        }
        if (user.getEmail() != null){
            u = new User();
            u.setEmail(user.getEmail());
            entity = userMapper.selectOne(u);
            if (entity != null && !entity.getId().equals(user.getId())){
                msg += "、邮箱";
            }
        }
        if (!StringUtils.isBlank(msg)){
            msg = msg.substring(1);
            msg += "不能重复";
        }

        return msg;
    }

    @Override
    public List<User> online() {
        return null;
    }
}
