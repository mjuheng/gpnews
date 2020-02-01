package com.gpnews.publish.controller;

import com.gpnews.publish.service.VisitsService;
import com.gpnews.utils.ShiroUtil;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/29
 */
@RestController
@RequestMapping("/visits")
public class VisitsController {

    @Autowired
    private VisitsService service;

    @RequiresPermissions("poster")
    @RequestMapping("")
    public CommonResult queryVisits(Integer days){
        String userId = ShiroUtil.getCurrUserId();
        List<Integer> list = service.selectVisits(userId, days);
        return ResultUtil.successSingleResult(list);
    }
}
