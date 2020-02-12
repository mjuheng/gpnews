package com.gpnews.admin.service;

import com.gpnews.pojo.Article;
import com.gpnews.pojo.vo.ArticleVo;

import java.util.List;

public interface ArticleService extends BaseService<Article>{
    List<ArticleVo> pageExclContent(ArticleVo article, String beginPubTime, String endPubTime, Integer currPage, Integer rows);

    Integer count(ArticleVo article, String beginPubTime, String endPubTime);

    ArticleVo getById(String id);
}
