package com.gpnews.publish.controller;

import com.gpnews.publish.service.ArticleService;
import com.news.pojo.Article;
import com.news.utils.result.CommonResult;
import com.news.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
@RestController
public class ArticleController {

    @Autowired
    private ArticleService service;

    @RequestMapping("")
    public CommonResult page(Article article, Integer currPage, Integer rows){
        List<Article> articles = service.pageExclContent(article, currPage, rows);
        return ResultUtil.successListResult(articles, currPage, rows, 11);
    }
}
