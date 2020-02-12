package com.gpnews.admin.controller;

import com.gpnews.admin.service.UserService;
import com.gpnews.pojo.User;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/2/1
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

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

    @RequiresPermissions({"userManage"})
    @RequestMapping("/delUser")
    public CommonResult delUser(String id){
        service.delById(id);
        return ResultUtil.successSingleResult(true, "用户删除成功");
    }

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

}
