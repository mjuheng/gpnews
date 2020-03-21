package com.gpnews.admin.service.impl;

import com.gpnews.admin.service.DynamicService;
import com.gpnews.dao.DynamicMapper;
import com.gpnews.pojo.Dynamic;
import com.gpnews.pojo.vo.DynamicVo;
import com.gpnews.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/3/20
 */
@Service
public class DynamicServiceImpl extends BaseServiceImpl<Dynamic> implements DynamicService {

    @Autowired
    private DynamicMapper mapper;

    @Override
    public List<DynamicVo> page(String userId, Integer currPage, Integer rows) {
        int start = PageUtil.getStart(currPage, rows);
        return mapper.page(userId, start, rows);
    }

    @Override
    public Integer count(String userId) {
        return mapper.count(userId);
    }

    @Override
    public Mapper<Dynamic> getMapper() {
        return mapper;
    }
}
