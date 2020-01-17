package com.gpnews.publish.service;

import com.news.pojo.Article;

import java.util.List;

public interface ArticleService extends BaseService<Article>{
    List<Article> pageExclContent(Article article, Integer currPage, Integer rows);
}
