package com.gpnews.admin.service.impl;

import com.gpnews.admin.service.InetArticleService;
import com.gpnews.dao.InetArticleMapper;
import com.gpnews.pojo.InetArticle;
import com.gpnews.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/3/29
 */
@Service
public class InetArticleServiceImpl extends BaseServiceImpl<InetArticle> implements InetArticleService {

    @Autowired
    private InetArticleMapper mapper;
    @Override
    public Mapper<InetArticle> getMapper() {
        return mapper;
    }

    @Override
    public List<InetArticle> page(Integer type, String endPubTime, Integer currPage, Integer rows) {
        int start = PageUtil.getStart(currPage, rows);
        return mapper.page(type, endPubTime, start, rows);
    }

    @Override
    public Integer count(Integer type, String endPubTime) {
        return mapper.count(type, endPubTime);
    }
}
