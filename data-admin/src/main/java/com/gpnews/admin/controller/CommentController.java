package com.gpnews.admin.controller;

import com.gpnews.admin.service.CommentService;
import com.gpnews.pojo.Comment;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangChongHeng
 * @date 2020/4/3
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService service;

    @RequestMapping("/count")
    public CommonResult count(Comment comment){
        return ResultUtil.successSingleResult(service.count(comment));
    }
}

