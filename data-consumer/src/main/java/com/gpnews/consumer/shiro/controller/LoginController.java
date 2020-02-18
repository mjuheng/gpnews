package com.gpnews.consumer.shiro.controller;


import com.gpnews.consumer.service.UserService;
import com.gpnews.consumer.shiro.realm.UserLoginToken;
import com.gpnews.consumer.shiro.service.ShiroService;
import com.gpnews.pojo.User;
import com.gpnews.utils.JsonUtil;
import com.gpnews.utils.ShiroUtil;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import com.gpnews.utils.result.SingleResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HuangChongHeng
 * @date 2019/12/31
 */
@RestController
public class LoginController {

    @Autowired
    private UserService service;

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
        }catch (LockedAccountException e){
            e.printStackTrace();
            return ResultUtil.errorSingleResult("账户被锁定，请联系客服解锁");
        }catch (DisabledAccountException e){
            e.printStackTrace();
            return ResultUtil.errorSingleResult(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.errorSingleResult("账号或者密码错误");
        }
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("sessionId", subject.getSession().getId());
        User userInfo = (User) subject.getPrincipal();
        userInfo.setPassword(null);
        retMap.put("user", JsonUtil.serialize(userInfo));
        retMap.put("message", "登录成功");
        return ResultUtil.successSingleResult(retMap);
    }

    @RequiresAuthentication
    @RequestMapping("/updateUser")
    public CommonResult updateUser(@RequestBody User user){
        if (!ShiroUtil.getCurrUserId().equals(user.getId()) && !SecurityUtils.getSubject().isPermitted("admin")){
            throw new AuthenticationException();
        }
        String ret = service.checkUserInfo(user);
        if (!StringUtils.isBlank(ret)){
            return ResultUtil.errorSingleResult(ret);
        }
        if (StringUtils.isBlank(user.getId())){
            service.insert(user);
        }else {
            user.setModifiedTime(new Date());
            service.getMapper().updateByPrimaryKeySelective(user);
        }
        return ResultUtil.successSingleResult(true);
    }

    @RequiresAuthentication
    @RequestMapping("/logout")
    public CommonResult logout(){
        SecurityUtils.getSubject().logout();
        return ResultUtil.successSingleResult(true, "退出成功");
    }

}
