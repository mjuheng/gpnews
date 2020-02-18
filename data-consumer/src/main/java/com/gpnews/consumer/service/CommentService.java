package com.gpnews.consumer.service;

import com.gpnews.pojo.Comment;
import com.gpnews.pojo.vo.CommentVo;

import java.util.List;

public interface CommentService extends BaseService<Comment> {

    List<CommentVo> page(Comment comment, Integer currPage, Integer rows);

    Integer count(Comment comment);

    CommentVo getById(String id);
}
