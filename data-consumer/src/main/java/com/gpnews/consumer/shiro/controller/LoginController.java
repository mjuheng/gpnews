package com.gpnews.consumer.shiro.controller;


import com.gpnews.consumer.service.UserService;
import com.gpnews.consumer.shiro.realm.UserLoginToken;
import com.gpnews.consumer.shiro.service.ShiroService;
import com.gpnews.pojo.User;
import com.gpnews.pojo.dto.UserDto;
import com.gpnews.utils.*;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.provider.MD5;
import tk.mybatis.mapper.entity.Example;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.*;

/**
 * @author HuangChongHeng
 * @date 2019/12/31
 */
@RestController
public class LoginController {

    @Autowired
    private UserService service;
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/login")
    public SingleResult login(@RequestBody User user){
        user.setPassword(Md5Util.encodeByMD5(user.getPassword()));
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

    @PostMapping("/register")
    public CommonResult register(@RequestBody @Valid UserDto user){
        user.setPassword(Md5Util.encodeByMD5(user.getPassword()));
        if (!user.getVerify().equals(redisUtil.get(user.getEmail()))){
            return ResultUtil.errorSingleResult("验证码错误");
        }
        user.setIsLock(false);
        String checkRet = service.checkUserInfo(user);
        if (!StringUtils.isBlank(checkRet)){
            return ResultUtil.errorSingleResult(checkRet);
        }
        service.insert(user);
        return ResultUtil.successSingleResult(true, "注册成功");
    }

    @RequiresAuthentication
    @RequestMapping("/updateUser")
    public CommonResult updateUser(@RequestBody User user){
        user.setPassword(Md5Util.encodeByMD5(user.getPassword()));
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

    @RequestMapping("/sendVerify")
    public CommonResult sendVerify(String address){
        try {
            String verify = getCode(6);
            redisUtil.set(address, verify, 180L);
            MailUtil.sendText("新闻发布平台--验证码", "验证码：" + verify + "。请尽快填写，三分钟后失效", address);
        } catch (MessagingException e) {
            return ResultUtil.errorSingleResult(false);
        }
        return ResultUtil.successSingleResult(true, "验证码发送成功");
    }

    @RequestMapping("/forget")
    public CommonResult forget(@RequestBody Map<String, String> map){
        String address = map.get("address");
        String verify = map.get("verify");
        String newPwd = map.get("newPwd");

        if (verify != null && !verify.equals(redisUtil.get(address))){
            return ResultUtil.errorSingleResult("验证码错误");
        }
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("email", address);
        User user = service.getMapper().selectByExample(example).get(0);
        user.setPassword(newPwd);
        service.updateNoNull(user);
        return ResultUtil.successSingleResult(true, "密码修改成功");
    }

    private String getCode(int n) {
        String string = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] ch = new char[n];
        for (int i = 0; i < n; i++) {
            Random random = new Random();
            int index = random.nextInt(string.length());
            ch[i] = string.charAt(index);
        }
        return String.valueOf(ch);
    }


    @RequiresAuthentication
    @RequestMapping("/changePwd")
    public CommonResult changePwd(@RequestBody Map<String, String> map){
        User user = service.load(ShiroUtil.getCurrUserId());
        String oldPwd = map.get("oldPwd");
//        String newPwd = map.get("newPwd");
        String newPwd = Md5Util.encodeByMD5(map.get("newPwd"));
        String againPwd = Md5Util.encodeByMD5(map.get("againPwd"));
//        String againPwd = map.get("againPwd");
        if (!newPwd.equals(againPwd)){
            return ResultUtil.errorSingleResult("密码不一致，请重新输入");
        }
        if (!user.getPassword().equals(oldPwd)){
            return ResultUtil.errorSingleResult("旧密码错误");
        }
        user.setPassword(newPwd);
        service.update(user);
        return ResultUtil.successSingleResult(true);
    }

    @RequiresAuthentication
    @RequestMapping("/logout")
    public CommonResult logout(){
        SecurityUtils.getSubject().logout();
        return ResultUtil.successSingleResult(true, "退出成功");
    }

    @RequiresAuthentication
    @RequestMapping("/checkLogin")
    public CommonResult checkLogin(){
        return ResultUtil.successSingleResult(true);
    }

    @RequestMapping("/checkPerm")
    public CommonResult checkPerm(String perm, HttpServletRequest request){
        if (!SecurityUtils.getSubject().isAuthenticated()){
            return ResultUtil.successSingleResult(false);
        }
        return ResultUtil.successSingleResult(true);
    }
}
