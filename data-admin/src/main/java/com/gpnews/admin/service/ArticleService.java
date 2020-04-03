package com.gpnews.admin.service;

import com.gpnews.pojo.Article;
import com.gpnews.pojo.vo.ArticleVo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ArticleService extends BaseService<Article>{
    List<ArticleVo> pageExclContent(ArticleVo article, String beginPubTime, String endPubTime, Integer currPage, Integer rows);

    Integer count(ArticleVo article, String beginPubTime, String endPubTime);

    ArticleVo getById(String id);

    Map<String, Object> countReadAndComm(String userId);

    List<Map<String, Object>> eachCount();
}
