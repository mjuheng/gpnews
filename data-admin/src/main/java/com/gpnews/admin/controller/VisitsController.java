package com.gpnews.admin.controller;

import com.gpnews.admin.annotation.SystemLog;
import com.gpnews.admin.service.VisitsService;
import com.gpnews.pojo.Visits;
import com.gpnews.utils.ShiroUtil;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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

    @SystemLog("查询访问量")
    @RequiresAuthentication
    @RequestMapping("")
    public CommonResult queryVisits(String userId, Integer type){
        List<Visits> list = service.selectByTime(userId, type);
        return ResultUtil.successSingleResult(list);
    }
}
