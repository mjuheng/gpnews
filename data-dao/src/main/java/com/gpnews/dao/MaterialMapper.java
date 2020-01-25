package com.gpnews.dao;

import com.gpnews.pojo.Material;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/20
 */
public interface MaterialMapper extends Mapper<Material> {
    List<Material> query(@Param("material") Material material,
                         @Param("start") Integer start,
                         @Param("rows") Integer rows);
    Integer count(@Param("material") Material material);
}
