package com.gpnews.admin.controller;

import com.gpnews.admin.service.TaskMonitorService;
import com.gpnews.pojo.TaskMonitor;
import com.gpnews.pojo.vo.TaskMonitorVo;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/4/4
 */
@RestController
@RequestMapping("/task/monitor")
public class TaskMonitorController {

    @Autowired
    private TaskMonitorService service;

    @RequestMapping("")
    public CommonResult page(String name, Integer currPage, Integer rows){
        List<TaskMonitorVo> dataList = service.page(name, currPage, rows);
        int count = service.count(name);
        return ResultUtil.successListResult(dataList, currPage, rows, count);
    }


    @RequestMapping("/del")
    public CommonResult del(String id){
        service.delById(id);
        return ResultUtil.successSingleResult(true);
    }
}
