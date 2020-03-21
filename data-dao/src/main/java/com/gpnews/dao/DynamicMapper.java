package com.gpnews.dao;

import com.gpnews.pojo.Dynamic;
import com.gpnews.pojo.vo.DynamicVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DynamicMapper extends Mapper<Dynamic> {

    List<DynamicVo> page(@Param("userId") String userId,
                         @Param("start") Integer start,
                         @Param("rows") Integer rows);

    Integer count(@Param("userId") String userId);
}
