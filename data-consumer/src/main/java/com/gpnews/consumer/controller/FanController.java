package com.gpnews.consumer.controller;

import com.gpnews.consumer.service.FanService;
import com.gpnews.pojo.Fan;
import com.gpnews.pojo.vo.FanVo;
import com.gpnews.utils.ShiroUtil;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
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


    @RequestMapping("/addFan")
    public CommonResult addFan(String toUserId){
        String id = ShiroUtil.getCurrUserId();
        Fan fan = new Fan(id, toUserId);
        service.insert(fan);
        return ResultUtil.successSingleResult(true, "关注成功");
    }

    @RequestMapping("/delFan")
    public CommonResult delFan(String toUserId){
        String id = ShiroUtil.getCurrUserId();
        service.delByBothUserId(id, toUserId);
        return ResultUtil.successSingleResult(true, "取消关注成功");
    }

    @RequestMapping("/isFan")
    public CommonResult isFan(@NotNull String toUserId){
        String id = ShiroUtil.getCurrUserId();
        return ResultUtil.successSingleResult(service.isFan(id, toUserId));
    }
}
