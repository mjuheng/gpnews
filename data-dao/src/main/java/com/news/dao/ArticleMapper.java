package com.news.dao;

import com.news.pojo.Article;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
public interface ArticleMapper extends Mapper<Article> {
    List<Article> pageExclContent(@Param("article") Article article,
                       @Param("currPage") Integer currPage,
                       @Param("rows") Integer rows);
}
