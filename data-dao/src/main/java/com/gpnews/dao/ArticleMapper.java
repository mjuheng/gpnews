package com.gpnews.dao;

import com.gpnews.pojo.Article;
import com.gpnews.pojo.vo.ArticleVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
public interface ArticleMapper extends Mapper<Article> {
    List<ArticleVo> pageExclContent(@Param("article") ArticleVo article,
                                    @Param("beginPubTime") String beginPubTime,
                                    @Param("endPubTime") String endPubTime,
                                    @Param("start") Integer start,
                                    @Param("rows") Integer rows);
    Integer count(@Param("article") ArticleVo article,
                  @Param("beginPubTime") String beginPubTime,
                  @Param("endPubTime") String endPubTime);


    List<ArticleVo> pageMP(@Param("article") ArticleVo article,
                           @Param("pubTime") String pubTime,
                           @Param("rows") Integer rows);

    ArticleVo getById(@Param("id") String id);

    List<ArticleVo> pageByFan(@Param("article") ArticleVo article,
                              @Param("beginPubTime") String beginPubTime,
                              @Param("endPubTime") String endPubTime,
                              @Param("userId") String userId,
                              @Param("start") Integer start,
                              @Param("rows") Integer rows);
    Integer countByFan(@Param("article") ArticleVo article,
                       @Param("userId") String userId,
                       @Param("beginPubTime") String beginPubTime,
                       @Param("endPubTime") String endPubTime);

    Map<String, Object> countReadAndComm(@Param("userId") String userId);

    List<Map<String, Object>> eachCount();

}
