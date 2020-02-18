package com.gpnews.consumer.service.impl;

import com.gpnews.consumer.service.ArticleService;
import com.gpnews.dao.ArticleMapper;
import com.gpnews.pojo.Article;
import com.gpnews.pojo.vo.ArticleVo;
import com.gpnews.utils.PageUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ArticleVo> pageExclContent(ArticleVo article, String beginPubTime, String endPubTime, Integer currPage, Integer rows) {

        Integer start = PageUtil.getStart(currPage, rows);
        return articleMapper.pageExclContent(article,beginPubTime, endPubTime, start, rows);
    }

    @Override
    public Integer count(ArticleVo article, String beginPubTime, String endPubTime) {
        return articleMapper.count(article, beginPubTime, endPubTime);
    }

    @Override
    public ArticleVo getById(String id) {
        return articleMapper.getById(id);
    }

    @Override
    public List<ArticleVo> pageMP(ArticleVo article, String pubTime, Integer rows) {
        return articleMapper.pageMP(article, pubTime, rows);
    }

    @Override
    public List<ArticleVo> pageExclContentByFan(ArticleVo article, String beginPubTime, String endPubTime, String userId, Integer currPage, Integer rows) {

        Integer start = PageUtil.getStart(currPage, rows);
        return articleMapper.pageByFan(article,beginPubTime, endPubTime, userId, start, rows);
    }

    @Override
    public Integer countByFan(ArticleVo article, String userId, String beginPubTime, String endPubTime) {
        return articleMapper.countByFan(article, userId, beginPubTime, endPubTime);
    }
}
