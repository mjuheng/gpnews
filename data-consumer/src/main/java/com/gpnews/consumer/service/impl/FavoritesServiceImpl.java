package com.gpnews.consumer.service.impl;

import com.gpnews.consumer.service.FavoritesService;
import com.gpnews.dao.FavoritesMapper;
import com.gpnews.pojo.Favorites;
import com.gpnews.pojo.vo.ArticleVo;
import com.gpnews.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/2/17
 */
@Service
public class FavoritesServiceImpl extends BaseServiceImpl<Favorites> implements FavoritesService {

    @Autowired
    private FavoritesMapper mapper;

    @Override
    public Mapper<Favorites> getMapper() {
        return mapper;
    }

    @Override
    public List<ArticleVo> page(String userId, String title, String endCreatedTime, Integer currPage, Integer rows) {
        int start = PageUtil.getStart(currPage, rows);
        return mapper.page(userId, title, endCreatedTime,start, rows);
    }

    @Override
    public Integer count(String userId, String title, String endCreatedTime) {
        return mapper.count(userId, title, endCreatedTime);
    }

    @Override
    public boolean isFavorites(String userId, String articleId) {
        Example example = new Example(Favorites.class);
        example.createCriteria().andEqualTo("userId", userId)
                                .andEqualTo("articleId", articleId);
        int count = mapper.selectCountByExample(example);
        return count > 0;
    }

    @Override
    public int del(String userId, String articleId) {
        Example example = new Example(Favorites.class);
        example.createCriteria().andEqualTo("userId", userId)
                                .andEqualTo("articleId", articleId);
        return mapper.deleteByExample(example);
    }
}
