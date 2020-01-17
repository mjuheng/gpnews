package com.gpnews.publish.service.impl;

import com.gpnews.publish.service.CategoryService;
import com.news.dao.CategoryMapper;
import com.news.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Mapper<Category> getMapper() {
        return categoryMapper;
    }
}
