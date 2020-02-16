package com.gpnews.consumer.service.impl;

import com.gpnews.consumer.service.CommentService;
import com.gpnews.dao.CommentMapper;
import com.gpnews.pojo.Comment;
import com.gpnews.pojo.Permission;
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
    public List<CommentVo> page(Comment comment, Integer currPage, Integer rows) {
        int start = PageUtil.getStart(currPage, rows);
        List<CommentVo> rootList = mapper.page(comment, true, start, rows);
        List<CommentVo> nodeList = mapper.page(comment, null, null, null);
        Map<String, List<CommentVo>> nodeMap = listToMap(nodeList);
        for (CommentVo root: rootList){
            root.setChildren(nodeMap.get(root.getId()));
        }
        return rootList;
    }

    private Map<String, List<CommentVo>> listToMap(List<CommentVo> nodeList) {
        Map<String, List<CommentVo>> map = new HashMap<>();
        for (CommentVo node : nodeList) {
            List<CommentVo> nodes = map.get(node.getParentId());
            if (nodes != null && nodes.size() > 0) {
                nodes.add(node);
            } else {
                List<CommentVo> list = new ArrayList<>();
                list.add(node);
                map.put(node.getParentId(), list);
            }
        }
        return map;
    }


    @Override
    public Integer count(Comment comment) {
        return mapper.count(comment, true);
    }

    @Override
    public Mapper<Comment> getMapper() {
        return mapper;
    }
}
