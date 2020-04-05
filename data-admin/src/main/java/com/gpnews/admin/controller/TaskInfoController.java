package com.gpnews.admin.controller;

import com.gpnews.admin.service.TaskInfoService;
import com.gpnews.pojo.TaskInfo;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangChongHeng
 * @date 2020/4/4
 */
@RestController
@RequestMapping("/task/info")
public class TaskInfoController {

    @Autowired
    private TaskInfoService service;

    @RequestMapping("")
    public CommonResult page(){
        return ResultUtil.successSingleResult(service.getMapper().selectAll());
    }

    @PostMapping("/update")
    public CommonResult update(@RequestBody TaskInfo taskInfo){
        if (taskInfo.getCron() != null && !checkCron(taskInfo.getCron())){
            return ResultUtil.errorSingleResult("cron表达式不合法");
        }
        service.updateNoNull(taskInfo);
        return ResultUtil.successSingleResult(true);
    }

    // 检查cron表达式合法性
    private boolean checkCron(String cron){
        String regMiao = "([0-9]{1,2}|[0-9]{1,2}\\-[0-9]{1,2}|\\*|[0-9]{1,2}\\/[0-9]{1,2}|[0-9]{1,2}\\,[0-9]{1,2})";
        String regFen = "\\s([0-9]{1,2}|[0-9]{1,2}\\-[0-9]{1,2}|\\*|[0-9]{1,2}\\/[0-9]{1,2}|[0-9]{1,2}\\,[0-9]{1,2})";
        String regShi = "\\s([0-9]{1,2}|[0-9]{1,2}\\-[0-9]{1,2}|\\*|[0-9]{1,2}\\/[0-9]{1,2}|[0-9]{1,2}\\,[0-9]{1,2})";
        String regRi = "\\s([0-9]{1,2}|[0-9]{1,2}\\-[0-9]{1,2}|\\*|[0-9]{1,2}\\/[0-9]{1,2}|[0-9]{1,2}\\,[0-9]{1,2}|\\?|L|W|C)";
        String regYue = "\\s([0-9]{1,2}|[0-9]{1,2}\\-[0-9]{1,2}|\\*|[0-9]{1,2}\\/[0-9]{1,2}|[0-9]{1,2}\\,[0-9]{1,2}|JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)";
        String regZhou = "\\s([1-7]{1}|[1-7]{1}\\-[1-7]{1}|[1-7]{1}\\#[1-7]{1}|\\*|[1-7]{1}\\/[1-7]{1}|[1-7]{1}\\,[1-7]{1}|MON|TUES|WED|THUR|FRI|SAT|SUN|\\?|L|C)";
        String regNian = "(\\s([0-9]{4}|[0-9]{4}\\-[0-9]{4}|\\*|[0-9]{4}\\/[0-9]{4}|[0-9]{4}\\,[0-9]{4})){0,1}";
        String pattern = regMiao + regFen + regShi + regRi + regYue + regZhou + regNian;
        return cron.matches(pattern);
    }

    @RequestMapping("/runTask")
    public CommonResult runTask(String id){
        service.runTask(id);
        return ResultUtil.successSingleResult(true);
    }
}
