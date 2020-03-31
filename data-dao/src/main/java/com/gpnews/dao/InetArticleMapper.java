package com.gpnews.dao;

import com.gpnews.pojo.InetArticle;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface InetArticleMapper extends Mapper<InetArticle> {
    List<InetArticle> page(@Param("type") Integer type,
                           @Param("endPubTime") String endPubTime,
                           @Param("start") Integer start,
                           @Param("rows") Integer rows);
    Integer count(@Param("type") Integer type,
                  @Param("endPubTime") String endPubTime);
}
