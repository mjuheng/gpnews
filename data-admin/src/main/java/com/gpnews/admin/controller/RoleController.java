package com.gpnews.admin.controller;

import com.gpnews.admin.annotation.SystemLog;
import com.gpnews.admin.service.RoleService;
import com.gpnews.pojo.Role;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/2/2
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService service;

    @SystemLog("查询角色")
    @RequiresPermissions({"userManage:roleManage"})
    @RequestMapping("")
    public CommonResult page(String name, Integer currPage, Integer rows){
        Role role = new Role();
        role.setName(name);
        List<Role> roleList = service.page(role, currPage, rows);
        Integer count = service.count(role);
        return ResultUtil.successListResult(roleList, currPage, rows, count);
    }

    @SystemLog("更新角色")
    @RequiresPermissions({"roleManage"})
    @PostMapping("/updateRole")
    public CommonResult updateRole(@RequestBody @Valid Role role){
        Integer ret = 0;
        if (StringUtils.isBlank(role.getId())){
            service.insert(role);
        }else {
            ret = service.updateWithParent(role);
        }
        if (ret == 1){
            return ResultUtil.errorSingleResult("父角色不能为自己");
        }
        return ResultUtil.successSingleResult(true);
    }

    @RequiresPermissions({"userManage:roleManage"})
    @GetMapping("/{id}")
    public CommonResult getById(@PathVariable String id){
        Role role = service.getMapper().selectByPrimaryKey(id);
        return ResultUtil.successSingleResult(role);
    }

    @SystemLog("查询角色")
    @RequiresPermissions({"userManage:roleManage"})
    @RequestMapping("/getTree")
    public CommonResult getTree(){
        return ResultUtil.successSingleResult( service.queryTree());
    }

    @SystemLog("删除角色")
    @RequiresPermissions({"roleManage"})
    @RequestMapping("/delRole")
    public CommonResult del(String id){
        service.delById(id);
        return ResultUtil.successSingleResult(true, "角色删除成功");
    }

    @RequestMapping("/getByUserId")
    public CommonResult getByUserId(String userId){
        return ResultUtil.successSingleResult(service.getRoleByUserId(userId));
    }

}
