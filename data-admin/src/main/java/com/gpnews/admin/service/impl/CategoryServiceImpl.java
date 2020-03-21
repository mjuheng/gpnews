package com.gpnews.admin.service.impl;

import com.gpnews.admin.service.CategoryService;
import com.gpnews.dao.CategoryMapper;
import com.gpnews.pojo.Category;
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
