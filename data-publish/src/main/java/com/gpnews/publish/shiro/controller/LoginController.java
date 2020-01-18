package com.gpnews.publish.shiro.controller;

import com.gpnews.publish.service.UserService;
import com.gpnews.publish.shiro.realm.UserLoginToken;
import com.gpnews.pojo.User;
import com.gpnews.utils.JsonUtil;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import com.gpnews.utils.result.SingleResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HuangChongHeng
 * @date 2019/12/31
 */
@RestController
public class LoginController {

    @RequestMapping("/login")
    public SingleResult login(@RequestBody User user){
        UserLoginToken token = new UserLoginToken(user.getUsername(), user.getPassword(), user);
        Subject subject = null;
        try {
            SecurityUtils.getSubject().login(token);
            subject = SecurityUtils.getSubject();
            User currentUser = (User) subject.getPrincipal();
            subject.getSession().setAttribute("id", currentUser.getId());
            subject.getSession().setAttribute("username", currentUser.getUsername());
            subject.getSession().setAttribute("sessionId", subject.getSession().getId());
        }catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.errorSingleResult("账号或者密码错误");
        }
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("sessionId", subject.getSession().getId());
        retMap.put("user", JsonUtil.serialize(subject.getPrincipal()));
        retMap.put("message", "登录成功");
        return ResultUtil.successSingleResult(retMap);
    }

    @RequestMapping("/logout")
    public CommonResult logout(){
        SecurityUtils.getSubject().logout();
        return ResultUtil.successSingleResult(true, "退出成功");
    }

}
