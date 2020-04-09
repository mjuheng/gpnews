package com.gpnews.admin.shiro.controller;

import com.gpnews.admin.service.RoleService;
import com.gpnews.admin.service.UserService;
import com.gpnews.admin.shiro.realm.UserLoginToken;
import com.gpnews.admin.shiro.service.ShiroService;
import com.gpnews.pojo.User;
import com.gpnews.pojo.dto.UserDto;
import com.gpnews.utils.JsonUtil;
import com.gpnews.utils.PageUtil;
import com.gpnews.utils.ShiroUtil;
import com.gpnews.utils.UUIDUtil;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import com.gpnews.utils.result.SingleResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DelegatingSession;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

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
    private SessionDAO sessionDAO;
    @Autowired
    private ShiroService shiroService;
    @Autowired
    private RoleService roleService;

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
    @Transactional
    public CommonResult updateUser(@RequestBody UserDto user){
        if (!ShiroUtil.getCurrUserId().equals(user.getId()) && !SecurityUtils.getSubject().isPermitted("userManage")){
            throw new AuthenticationException();
        }
        String ret = service.checkUserInfo(user);
        if (!StringUtils.isBlank(ret)){
            return ResultUtil.errorSingleResult(ret);
        }
        // 修改用户表
        if (StringUtils.isBlank(user.getId())){
            service.insert(user);
        }else {
            service.updateNoNull(user);
        }
        // 修改用户角色表
        roleService.delByUserId("1");
        if (!StringUtils.isBlank(user.getRoleId())){
            Map<String, Object> map = new HashMap<>();
            map.put("userId", user.getId());
            String[] roleIds = user.getRoleId().split(";");
            for (String roleId : roleIds) {
                map.put("id", UUIDUtil.create());
                map.put("roleId", roleId);
                roleService.insertRoleUser(map);
            }
        }

        return ResultUtil.successSingleResult(true);
    }

    @RequiresAuthentication
    @RequestMapping("/logout")
    public CommonResult logout(){
        SecurityUtils.getSubject().logout();
        return ResultUtil.successSingleResult(true, "退出成功");
    }

    @RequestMapping("/online")
    public  CommonResult online(String username, Integer currPage, Integer rows){
        Map<String, Object> map = shiroService.pageSession(username, currPage, rows);
        List<Session> sessionList = (List<Session>) map.get("list");
        Integer count = (Integer) map.get("count");
        return ResultUtil.successListResult(sessionList, currPage, rows, count);
    }

    @RequiresPermissions("admin")
    @RequestMapping("/kickout")
    public CommonResult kickout(String id){
        String[] ids = id.split(";");
        for (String sessionId: ids){
            Session session = sessionDAO.readSession(sessionId);
            sessionDAO.delete(session);
        }
        return ResultUtil.successSingleResult(true);
    }
}
