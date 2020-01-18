package com.gpnews.publish.service;

import com.gpnews.pojo.Article;

import java.util.List;

public interface ArticleService extends BaseService<Article>{
    List<Article> pageExclContent(Article article, String beginPubTime, String endPubTime, Integer currPage, Integer rows);

    Integer count(Article article, String beginPubTime, String endPubTime);
}
