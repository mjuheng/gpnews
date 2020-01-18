package com.gpnews.publish.controller;

import com.gpnews.publish.service.CategoryService;
import com.gpnews.pojo.Category;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public CommonResult selectAll(){
        List<Category> categories = categoryService.getMapper().selectAll();
        return ResultUtil.successListResult(categories);
    }
}
