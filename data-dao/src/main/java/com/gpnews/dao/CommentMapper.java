package com.gpnews.dao;

import com.gpnews.pojo.Comment;
import com.gpnews.pojo.vo.CommentVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/2/14
 */
public interface CommentMapper extends Mapper<Comment> {

    List<CommentVo> page(@Param("comment") Comment comment,
                         @Param("isParent") Boolean isParent,
                         @Param("start") Integer start,
                         @Param("rows") Integer rows);

    Integer count(@Param("comment") Comment comment,
                  @Param("isParent") Boolean isParent);
}
