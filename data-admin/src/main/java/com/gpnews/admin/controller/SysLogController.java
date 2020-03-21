package com.gpnews.admin.controller;

import com.gpnews.admin.annotation.SystemLog;
import com.gpnews.admin.service.SysLogService;
import com.gpnews.pojo.vo.SyslogVo;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/3/17
 */
@RestController
@RequestMapping("/log")
public class SysLogController {

    @Autowired
    private SysLogService service;

    @RequiresPermissions("systemLog")
    @RequestMapping("")
    public CommonResult page(String username, String opt, Integer currPage, Integer rows){
        List<SyslogVo> dataList = service.page(username, opt, currPage, rows);
        Integer count = service.count(username, opt);
        return ResultUtil.successListResult(dataList, currPage, rows, count);
    }

    @SystemLog("删除日志")
    @RequiresPermissions({"systemLog"})
    @RequestMapping("/delLog")
    public CommonResult del(String id){
        service.delById(id);
        return ResultUtil.successSingleResult(true, "日志删除成功");
    }
}
