package com.gpnews.consumer.service;

import com.gpnews.pojo.Favorites;

public interface FavoritesService extends BaseService<Favorites> {

    boolean isFavorites(String userId, String articleId);

    int del(String userId, String articleId);
}
