package com.gpnews.consumer.service;

import com.gpnews.pojo.Article;
import com.gpnews.pojo.Favorites;
import com.gpnews.pojo.vo.ArticleVo;

import java.util.List;

public interface FavoritesService extends BaseService<Favorites> {

    List<ArticleVo> page(String userId, String title, String endCreatedTime, Integer currPage, Integer rows);

    Integer count(String userId, String title, String endCreatedTime);

    boolean isFavorites(String userId, String articleId);

    int del(String userId, String articleId);
}
