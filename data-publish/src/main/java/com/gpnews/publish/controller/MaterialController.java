package com.gpnews.publish.controller;

import com.gpnews.pojo.Material;
import com.gpnews.publish.service.MaterialService;
import com.gpnews.utils.ShiroUtil;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/20
 */
@RestController
@RequestMapping("/material")
@RequiresAuthentication
public class MaterialController {

    @Autowired
    private MaterialService service;

    @RequestMapping("")
    public CommonResult query(Integer currPage, Integer rows){
        String userId = ShiroUtil.getCurrUserId();
        Material mater = new Material(userId);
        List<Material> materials = service.query(mater, currPage, rows);
        Integer count = service.count(mater);
        return ResultUtil.successListResult(materials, currPage, rows, count);
    }

    @RequestMapping("/add")
    public CommonResult add(@RequestBody @Valid Material material){
        String userId = ShiroUtil.getCurrUserId();
        material.setUserId(userId);
        service.insert(material);
        return ResultUtil.successSingleResult(true, "素材添加成功");
    }

    @RequestMapping("/del/{id}")
    public CommonResult del(@PathVariable("id") String id){
        service.delById(id);
        return ResultUtil.successSingleResult(true, "素材删除成功");
    }
}
