package com.news.portal.controller;

import com.news.pojo.User;
import com.news.portal.service.UserService;
import com.news.portal.shiro.realm.UserLoginToken;
import com.news.utils.RedisUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author HuangChongHeng
 * @date 2019/12/18
 */
@RestController
public class DemoController {
    @Autowired
    private RedisUtils redisUtils;
    @Resource
    private UserService userServiceImpl;

    @RequestMapping("/login")
    public String hello(User user){
        UserLoginToken token = new UserLoginToken(user.getUsername(), user.getPassword(), user);
        try {
            SecurityUtils.getSubject().login(token);
            Subject subject = SecurityUtils.getSubject();
            User currentUser = (User) subject.getPrincipal();
            subject.getSession().setAttribute("id", currentUser.getId());
            subject.getSession().setAttribute("username", currentUser.getUsername());
            subject.getSession().setAttribute("sessionId", subject.getSession().getId());
            System.out.println("登录成功");
        }catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "登录成功";

    }

}
