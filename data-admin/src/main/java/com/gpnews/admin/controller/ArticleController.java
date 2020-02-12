package com.gpnews.admin.controller;

import com.gpnews.admin.service.ArticleService;
import com.gpnews.pojo.Article;
import com.gpnews.pojo.dto.ArticleDto;
import com.gpnews.pojo.vo.ArticleVo;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
    public CommonResult page(ArticleVo article, String beginPubTime, String endPubTime, Integer currPage, Integer rows){
        List<ArticleVo> articles = service.pageExclContent(article, beginPubTime, endPubTime, currPage, rows);
        Integer count = service.count(article, beginPubTime, endPubTime);
        return ResultUtil.successListResult(articles, currPage, rows, count);
    }

    @RequestMapping("/changeStatus")
    public CommonResult changeStatus(String id, Integer status, String comment){
        Article article = new Article();
        article.setId(id);
        article.setStatus(status);
        if (status == 2){
            article.setPublishTime(new Date());
        }
        service.updateNoNull(article);
        return ResultUtil.successSingleResult(true);
    }

    @RequestMapping("/{id}")
    public CommonResult getById(@PathVariable("id") String id){
        return ResultUtil.successSingleResult(service.getById(id));
    }
}
