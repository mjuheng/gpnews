package com.gpnews.consumer.controller;

import com.gpnews.consumer.service.MsgService;
import com.gpnews.pojo.Msg;
import com.gpnews.utils.ShiroUtil;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author HuangChongHeng
 * @date 2020/4/6
 */
@RestController
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    private MsgService service;

    @RequiresAuthentication
    @RequestMapping("/add")
    public CommonResult add(@RequestBody @Valid Msg msg){
        String userId = ShiroUtil.getCurrUserId();
        msg.setOptUserId(userId);
        msg.setIsRead(false);
        service.insert(msg);
        return ResultUtil.successSingleResult(true, "反馈成功");
    }
}
