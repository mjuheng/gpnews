package com.gpnews.admin.service;

import com.gpnews.pojo.InetArticle;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/3/29
 */
public interface InetArticleService extends BaseService<InetArticle>{
    List<InetArticle> page(Integer type, String endPubTime, Integer currPage, Integer rows);
    Integer count(Integer type, String endPubTime);
}
