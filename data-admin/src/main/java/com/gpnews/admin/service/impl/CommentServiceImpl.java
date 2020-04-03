package com.gpnews.admin.service.impl;

import com.gpnews.admin.service.CommentService;
import com.gpnews.dao.CommentMapper;
import com.gpnews.pojo.Comment;
import com.gpnews.pojo.vo.CommentVo;
import com.gpnews.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HuangChongHeng
 * @date 2020/2/14
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentService {

    @Autowired
    private CommentMapper mapper;


    @Override
    public Integer count(Comment comment) {
        return mapper.count(comment, true);
    }

    @Override
    public Mapper<Comment> getMapper() {
        return mapper;
    }
}
