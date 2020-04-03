package com.gpnews.admin.service;

import com.gpnews.pojo.Comment;
import com.gpnews.pojo.vo.CommentVo;

import java.util.List;

public interface CommentService extends BaseService<Comment> {

    Integer count(Comment comment);

}
