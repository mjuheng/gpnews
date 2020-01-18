package com.gpnews.publish.shiro.controller;

import com.gpnews.publish.shiro.realm.UserLoginToken;
import com.gpnews.pojo.User;
import com.gpnews.utils.result.ResultUtil;
import com.gpnews.utils.result.SingleResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangChongHeng
 * @date 2019/12/31
 */
@RestController
public class LoginController {

    @RequestMapping("/login")
    public SingleResult login(@RequestBody User user){
        UserLoginToken token = new UserLoginToken(user.getUsername(), user.getPassword(), user);
        SecurityUtils.getSubject().login(token);
        Subject subject = SecurityUtils.getSubject();
        try {
            User currentUser = (User) subject.getPrincipal();
            subject.getSession().setAttribute("id", currentUser.getId());
            subject.getSession().setAttribute("username", currentUser.getUsername());
            subject.getSession().setAttribute("sessionId", subject.getSession().getId());
        }catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.errorSingleResult("账号或者密码错误");
        }
        return ResultUtil.successSingleResult(subject.getSession().getId());
    }
}
