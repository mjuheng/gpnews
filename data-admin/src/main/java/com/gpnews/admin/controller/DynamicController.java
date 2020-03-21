package com.gpnews.admin.controller;

import com.gpnews.admin.service.DynamicService;
import com.gpnews.pojo.vo.DynamicVo;
import com.gpnews.utils.ShiroUtil;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/3/20
 */
@RestController
@RequestMapping("/dynamic")
public class DynamicController {

    @Autowired
    private DynamicService service;

    @RequiresAuthentication
    @RequestMapping("")
    public CommonResult page(Integer currPage, Integer rows){
        String userId = ShiroUtil.getCurrUserId();
        List<DynamicVo> dataList = service.page(userId, currPage, rows);
        Integer count = service.count(userId);
        return ResultUtil.successListResult(dataList, currPage, rows, count);
    }
}
