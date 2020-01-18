package com.gpnews.publish.service.impl;

import com.gpnews.publish.service.ArticleService;
import com.gpnews.dao.ArticleMapper;
import com.gpnews.pojo.Article;
import com.gpnews.utils.PageUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Mapper<Article> getMapper() {
        return articleMapper;
    }

    @Override
    public List<Article> pageExclContent(Article article, String beginPubTime, String endPubTime, Integer currPage, Integer rows) {
        Integer start = PageUtil.getStart(currPage, rows);
        return articleMapper.pageExclContent(article,beginPubTime, endPubTime, start, rows);
    }

    @Override
    public Integer count(Article article, String beginPubTime, String endPubTime) {
        return articleMapper.count(article, beginPubTime, endPubTime);
    }
}
