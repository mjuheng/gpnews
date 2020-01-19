package com.gpnews.publish.controller;

import com.gpnews.pojo.User;
import com.gpnews.publish.service.ArticleService;
import com.gpnews.pojo.Article;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
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

    @RequiresPermissions("queryArticle")
    @RequestMapping("")
    public CommonResult page(Article article, String beginPubTime, String endPubTime, Integer currPage, Integer rows){
        Subject subject = SecurityUtils.getSubject();
        String id = ((User) subject.getPrincipal()).getId();
        article.setUserId(id);
        List<Article> articles = service.pageExclContent(article, beginPubTime, endPubTime, currPage, rows);
        Integer count = service.count(article, beginPubTime, endPubTime);
        return ResultUtil.successListResult(articles, currPage, rows, count);
    }
}
