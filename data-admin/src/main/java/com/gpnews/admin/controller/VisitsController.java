package com.gpnews.admin.controller;

import com.gpnews.admin.annotation.SystemLog;
import com.gpnews.admin.service.VisitsService;
import com.gpnews.utils.ShiroUtil;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
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
    @RequestMapping("")
    public CommonResult queryVisits(Integer days){
        String userId = ShiroUtil.getCurrUserId();
        List<Integer> list = service.selectVisits(userId, days);
        return ResultUtil.successSingleResult(list);
    }
}
