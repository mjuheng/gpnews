package com.gpnews.dao;

import com.gpnews.pojo.Favorites;
import com.gpnews.pojo.vo.ArticleVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface FavoritesMapper extends Mapper<Favorites> {

    List<ArticleVo> page(String userId, String title, String endCreatedTime, Integer start, Integer rows);

    Integer count(String userId, String title, String endCreatedTime);
}
