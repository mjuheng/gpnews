package com.gpnews.dao;

import com.gpnews.pojo.Article;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
public interface ArticleMapper extends Mapper<Article> {
    List<Article> pageExclContent(@Param("article") Article article,
                                  @Param("beginPubTime") String beginPubTime,
                                  @Param("endPubTime") String endPubTime,
                                  @Param("start") Integer start,
                                  @Param("rows") Integer rows);
    Integer count(@Param("article") Article article,
                  @Param("beginPubTime") String beginPubTime,
                  @Param("endPubTime") String endPubTime);
}
