package com.gpnews.dao;

import com.gpnews.pojo.Msg;
import com.gpnews.pojo.vo.MsgVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/4/1
 */
public interface MsgMapper extends Mapper<Msg> {
    List<MsgVo> page(@Param("msg") Msg msg,
                     @Param("start") Integer start,
                     @Param("rows") Integer rows);

    Integer count(@Param("msg") Msg msg);
}
