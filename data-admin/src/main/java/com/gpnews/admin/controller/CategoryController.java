package com.gpnews.admin.controller;

import com.gpnews.admin.annotation.SystemLog;
import com.gpnews.admin.service.CategoryService;
import com.gpnews.pojo.Category;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    public CommonResult page(Integer currPage, Integer rows){
        List<Category> categories = service.page(currPage, rows);
        int count = service.getMapper().selectCount(null);
        return ResultUtil.successListResult(categories, currPage, rows, count);
    }

    @RequiresPermissions("categoryManage")
    @SystemLog("操作分类信息")
    @PostMapping("/addOrUpdate")
    public CommonResult update(@RequestBody @Valid Category category){
        if (service.checkNameExist(category.getId(), category.getName())){
            return ResultUtil.errorSingleResult("分类名已经存在");
        }

        if (category.getId() == null) {
            service.insert(category);
        }else {
            service.update(category);
        }
        return ResultUtil.successSingleResult(true);
    }

    @RequestMapping("/del")
    public CommonResult del(String id){
        if (id.split(",").length >= service.getMapper().selectCount(null)) {
            return ResultUtil.errorSingleResult(false, "删除失败，至少保留一项分类");
        }
        service.delById(id);
        return ResultUtil.successSingleResult(true);
    }

}
