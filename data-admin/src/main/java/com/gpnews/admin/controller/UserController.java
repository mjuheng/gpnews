package com.gpnews.admin.controller;

import com.gpnews.admin.annotation.SystemLog;
import com.gpnews.admin.service.UserService;
import com.gpnews.pojo.User;
import com.gpnews.utils.ShiroUtil;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author HuangChongHeng
 * @date 2020/2/1
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @SystemLog("查询用户")
    @RequiresPermissions({"userManage"})
    @RequestMapping("")
    public CommonResult page(String username, Boolean isLock, Integer currPage, Integer rows){
        User user = new User();
        user.setUsername(username);
        user.setIsLock(isLock);
        List<User> userList = service.pageExclPwd(user, currPage, rows);
        Integer total = service.count(user);
        return ResultUtil.successListResult(userList, currPage, rows, total);
    }

    @RequestMapping("/countUser")
    public CommonResult countUser(User user){
        return ResultUtil.successSingleResult(service.count(user));
    }

    @SystemLog("删除用户")
    @RequiresPermissions({"userManage"})
    @RequestMapping("/delUser")
    public CommonResult delUser(String id){
        service.delById(id);
        return ResultUtil.successSingleResult(true, "用户删除成功");
    }

    @SystemLog("锁定、解锁用户账号")
    @RequiresPermissions({"userManage"})
    @RequestMapping("/changeLock")
    public CommonResult changeLock(String id, Boolean isLock){
        List<User> userList = new ArrayList<>();
        User user;
        String[] ids = id.split(",");
        for (int i = 0; i < ids.length; i++){
            user = new User();
            user.setId(ids[i]);
            user.setIsLock(isLock);
            userList.add(user);
        }
        service.updateBatch(userList);
        return ResultUtil.successSingleResult(true);
    }

    @SystemLog("更改密码")
    @RequestMapping("/changePwd")
    public CommonResult changePwd(@RequestBody Map<String, String> map){
        User user = service.load(ShiroUtil.getCurrUserId());
        String oldPwd = map.get("oldPwd");
        String newPwd = map.get("newPwd");
        String againPwd = map.get("againPwd");
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

}
