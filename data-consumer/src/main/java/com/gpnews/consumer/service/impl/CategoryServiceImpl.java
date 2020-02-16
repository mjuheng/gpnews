package com.gpnews.consumer.service.impl;

import com.gpnews.consumer.service.CategoryService;
import com.gpnews.dao.CategoryMapper;
import com.gpnews.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author HuangChongHeng
 * @date 2020/2/13
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {

    @Autowired
    private CategoryMapper mapper;

    @Override
    public Mapper<Category> getMapper() {
        return mapper;
    }
}
