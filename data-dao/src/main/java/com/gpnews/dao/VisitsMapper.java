package com.gpnews.dao;

import com.gpnews.pojo.Visits;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface VisitsMapper extends Mapper<Visits> {

    List<Visits> selectSinceTime(@Param("userId") String userId,
                                 @Param("sinceTime") Date sinceTime);

    Integer countSinceTime(@Param("userId") String userId,
                           @Param("sinceTime") Date sinceTime);

}
