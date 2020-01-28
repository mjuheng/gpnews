package com.gpnews.dao;

import com.gpnews.pojo.Fan;
import com.gpnews.pojo.User;
import com.gpnews.pojo.vo.FanVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/27
 */
public interface FanMapper extends Mapper<Fan> {
    List<Fan> page(@Param("fan") Fan fan,
                   @Param("start") Integer start,
                   @Param("rows") Integer rows);

    List<FanVo> pageFans(@Param("id") String id,
                         @Param("start") Integer start,
                         @Param("rows") Integer rows);

    Integer countFans(@Param("id") String id);
}
