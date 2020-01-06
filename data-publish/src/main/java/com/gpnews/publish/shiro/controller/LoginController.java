package com.gpnews.publish.shiro.controller;

import com.gpnews.publish.shiro.realm.UserLoginToken;
import com.news.pojo.User;
import com.news.utils.result.ResultUtil;
import com.news.utils.result.SingleResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangChongHeng
 * @date 2019/12/31
 */
@RestController
public class LoginController {

    @RequestMapping("/login")
    public SingleResult login(User user){
        UserLoginToken token = new UserLoginToken(user.getUsername(), user.getPassword(), user);
        try {
            SecurityUtils.getSubject().login(token);
            Subject subject = SecurityUtils.getSubject();
            User currentUser = (User) subject.getPrincipal();
            subject.getSession().setAttribute("id", currentUser.getId());
            subject.getSession().setAttribute("username", currentUser.getUsername());
            subject.getSession().setAttribute("sessionId", subject.getSession().getId());
        }catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.errorSingleResult("账号或者密码错误");
        }
        return ResultUtil.successSingleResult("登录成功");
    }
}
