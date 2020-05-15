package com.gpnews.admin.service.impl;

import com.gpnews.admin.service.ArticleService;
import com.gpnews.dao.ArticleMapper;
import com.gpnews.pojo.Article;
import com.gpnews.pojo.vo.ArticleVo;
import com.gpnews.utils.PageUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> countReadAndComm(String userId) {
        return articleMapper.countReadAndComm(userId);
    }

    @Override
    public List<Map<String, Object>> eachCount() {
        List<Map<String, Object>> list = articleMapper.eachCount();
        int total = count(null, null, null);
        for (Map<String, Object> map : list) {
            double percent = Double.parseDouble(map.get("num").toString()) / total * 100;
            map.put("percent", Integer.parseInt(String.format("%.0f", percent)));
        }
        return list;
    }
}
