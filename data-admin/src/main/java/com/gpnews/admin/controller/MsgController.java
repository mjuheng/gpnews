package com.gpnews.admin.controller;

import com.gpnews.admin.service.MsgService;
import com.gpnews.pojo.Msg;
import com.gpnews.pojo.vo.MsgVo;
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
 * @date 2020/4/1
 */
@RestController
@RequestMapping("/msg")
@RequiresAuthentication
public class MsgController {

    @Autowired
    private MsgService service;

    @RequestMapping("")
    public CommonResult page(Msg msg, Integer currPage, Integer rows){
        if (msg.getType() == 2){
            msg.setUserId(ShiroUtil.getCurrUserId());
        }
        List<MsgVo> dataList = service.page(msg, currPage, rows);

        Integer count = service.count(msg);
        return ResultUtil.successListResult(dataList, currPage, rows, count);
    }

    @RequestMapping("/updateRead")
    public CommonResult updateType(String id, Boolean read){
        service.updateRead(id, read);
        return ResultUtil.successSingleResult(false);
    }

    @RequestMapping("/del")
    public CommonResult del(String id){
        service.delById(id);
        return ResultUtil.successSingleResult(true);
    }

}
