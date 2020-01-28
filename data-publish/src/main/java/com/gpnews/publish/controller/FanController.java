package com.gpnews.publish.controller;

import com.gpnews.pojo.User;
import com.gpnews.pojo.vo.FanVo;
import com.gpnews.publish.service.FanService;
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
 * @date 2020/1/27
 */
@RestController
@RequiresAuthentication
@RequestMapping("/fan")
public class FanController {

    @Autowired
    private FanService service;

    @RequestMapping("/pageFans")
    public CommonResult pageFans(Integer currPage, Integer rows){
        String id = ShiroUtil.getCurrUserId();
        List<FanVo> fans = service.pageFans(id, currPage, rows);
        Integer count = service.countFans(id);
        return ResultUtil.successListResult(fans, currPage, rows, count);
    }
}
