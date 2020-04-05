package com.gpnews.consumer.service;

import com.gpnews.pojo.Article;
import com.gpnews.pojo.vo.ArticleVo;

import java.util.List;
import java.util.Map;

public interface ArticleService extends BaseService<Article>{

    List<ArticleVo> pageExclContent(ArticleVo article, String beginPubTime, String endPubTime, Integer currPage, Integer rows);

    Integer count(ArticleVo article, String beginPubTime, String endPubTime);

    List<ArticleVo> pageMP(ArticleVo article, String pubTime, Integer rows);

    ArticleVo getById(String id);

    List<ArticleVo> pageExclContentByFan(ArticleVo article, String beginPubTime, String endPubTime, String userId, Integer currPage, Integer rows);

    Integer countByFan(ArticleVo article, String userId, String beginPubTime, String endPubTime);

    void addNum(String id, Integer type);

    Map<String, Object> countReadAndCount(String id);
}
