package com.gpnews.consumer.controller;

import com.gpnews.consumer.service.ArticleService;
import com.gpnews.consumer.service.InetArticleService;
import com.gpnews.pojo.InetArticle;
import com.gpnews.pojo.vo.ArticleVo;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/3/30
 */
@RestController
@RequestMapping("/inetArticle")
public class InetArticleController {

    @Autowired
    private InetArticleService service;

    @RequestMapping("")
    public CommonResult page(String endPubTime, Integer currPage, Integer rows){
        List<InetArticle> articles = service.page(null, endPubTime, currPage, rows);
        Integer count = service.count(null, endPubTime);
        return ResultUtil.successListResult(articles, currPage, rows, count);
    }

    @GetMapping("/{id}")
    public CommonResult getById(@PathVariable("id") String id){
        return ResultUtil.successSingleResult(service.load(id));
    }
}
