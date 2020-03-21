package com.gpnews.admin.controller;

import com.gpnews.admin.annotation.SystemLog;
import com.gpnews.admin.service.PermissionService;
import com.gpnews.pojo.vo.PermissionVo;
import com.gpnews.utils.ShiroUtil;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/2/3
 */
@RestController
@RequestMapping("/perm")
public class PermissionController {

    @Autowired
    private PermissionService service;

    @SystemLog("查询权限")
    @RequiresPermissions("roleManage")
    @RequestMapping("/getTree")
    public CommonResult getTree(){
        return ResultUtil.successSingleResult( service.queryTree());
    }

    @SystemLog("查询权限")
    @RequiresAuthentication
    @RequestMapping("/getPerm")
    public CommonResult getPerm(){
        String currUserId = ShiroUtil.getCurrUserId();
        return ResultUtil.successSingleResult(service.getTreeByUser(currUserId));
    }
}
