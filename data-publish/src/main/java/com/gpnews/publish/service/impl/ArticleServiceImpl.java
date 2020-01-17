package com.gpnews.publish.service.impl;

import com.gpnews.publish.service.ArticleService;
import com.news.dao.ArticleMapper;
import com.news.pojo.Article;
import com.news.utils.PageUtil;
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
    public List<Article> pageExclContent(Article article, Integer currPage, Integer rows) {
        Integer start = PageUtil.getStart(currPage, rows);
        articleMapper.pageExclContent(article, currPage, rows);
        return null;
    }
}
