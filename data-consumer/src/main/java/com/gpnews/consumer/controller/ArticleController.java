package com.gpnews.consumer.controller;

import com.gpnews.consumer.service.ArticleService;
import com.gpnews.pojo.Article;
import com.gpnews.pojo.vo.ArticleVo;
import com.gpnews.utils.ShiroUtil;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/2/13
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService service;

    @RequestMapping("")
    public CommonResult page(ArticleVo article, String beginPubTime, String endPubTime, Integer currPage, Integer rows){
        List<ArticleVo> articles = service.pageExclContent(article, beginPubTime, endPubTime, currPage, rows);
        Integer count = service.count(article, beginPubTime, endPubTime);
        return ResultUtil.successListResult(articles, currPage, rows, count);
    }

    @GetMapping("/{id}")
    public CommonResult getById(@PathVariable("id") String id){
        return ResultUtil.successSingleResult(service.getById(id));
    }

    /**
     *
     * @param id
     * @param type 1. 阅读  2. 回复
     * @return
     */
    @GetMapping("/addNum")
    public void addNum(String id, Integer type){
        service.addNum(id, type);
    }

    @RequiresAuthentication
    @GetMapping("/pageByFan")
    public CommonResult pageByFan(ArticleVo article, String beginPubTime, String endPubTime, Integer currPage, Integer rows){
        String userId = ShiroUtil.getCurrUserId();
        List<ArticleVo> articles = service.pageExclContentByFan(article, beginPubTime, endPubTime, userId, currPage, rows);
        Integer count = service.countByFan(article, userId, beginPubTime, endPubTime);
        return ResultUtil.successListResult(articles, currPage, rows, count);
    }


    @GetMapping("/countReadAndComm/{id}")
    public CommonResult countReadAndComm(@PathVariable("id") String id){
        return ResultUtil.successSingleResult(service.countReadAndCount(id));
    }
}
