package com.gpnews.admin.controller;

import com.gpnews.admin.annotation.SystemLog;
import com.gpnews.admin.service.CategoryService;
import com.gpnews.pojo.Category;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @SystemLog("查询分类")
    @GetMapping("")
    public CommonResult selectAll(){
        List<Category> categories = service.getMapper().selectAll();
        return ResultUtil.successListResult(categories);
    }

    @PostMapping("/add")
    public CommonResult add(@RequestBody @Valid Category category){
        if (service.getMapper().selectOne(category) != null){
            return ResultUtil.errorSingleResult("分类名已经存在");
        }
        service.insert(category);
        return ResultUtil.successSingleResult(true);
    }
}
