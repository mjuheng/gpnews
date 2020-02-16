package com.gpnews.consumer.controller;

import com.gpnews.consumer.service.CategoryService;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangChongHeng
 * @date 2020/2/13
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @RequestMapping("/all")
    public CommonResult all(){
        return ResultUtil.successSingleResult(service.getMapper().selectAll());
    }
}
