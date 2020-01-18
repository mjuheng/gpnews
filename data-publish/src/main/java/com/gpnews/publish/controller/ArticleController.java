package com.gpnews.publish.controller;

import com.gpnews.publish.service.ArticleService;
import com.gpnews.pojo.Article;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService service;

    @RequestMapping("")
    public CommonResult page(Article article, String beginPubTime, String endPubTime, Integer currPage, Integer rows){
        List<Article> articles = service.pageExclContent(article, beginPubTime, endPubTime, currPage, rows);
        Integer count = service.count(article, beginPubTime, endPubTime);
        return ResultUtil.successListResult(articles, currPage, rows, count);
    }
}
