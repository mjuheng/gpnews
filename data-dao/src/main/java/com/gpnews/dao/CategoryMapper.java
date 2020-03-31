package com.gpnews.dao;

import com.gpnews.pojo.Category;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
public interface CategoryMapper extends Mapper<Category> {
    List<Category> page(@Param("start") Integer start,
                        @Param("rows") Integer rows);
}
