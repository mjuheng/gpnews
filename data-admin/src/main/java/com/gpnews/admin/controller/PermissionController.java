package com.gpnews.admin.controller;

import com.gpnews.admin.service.PermissionService;
import com.gpnews.pojo.vo.PermissionVo;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
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

    @RequiresPermissions("queryPermission")
    @RequestMapping("/getTree")
    public CommonResult getTree(){
        return ResultUtil.successSingleResult( service.queryTree());
    }

}
