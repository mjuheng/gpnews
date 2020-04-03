package com.gpnews.admin.service.impl;

import com.gpnews.admin.service.ArticleService;
import com.gpnews.admin.service.CategoryService;
import com.gpnews.dao.CategoryMapper;
import com.gpnews.pojo.Category;
import com.gpnews.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {

    @Resource
    private CategoryMapper mapper;
    @Autowired
    private ArticleService articleService;

    @Override
    public Mapper<Category> getMapper() {
        return mapper;
    }

    @Override
    public List<Category> page(Integer currPage, Integer rows) {
        int start = PageUtil.getStart(currPage, rows);
        return mapper.page(start, rows);
    }

    @Override
    public boolean checkNameExist(String id, String name) {
        Category category = new Category();
        category.setName(name);
        category = mapper.selectOne(category);
        return category != null && !category.getId().equals(id);
    }

    @Override
    public void syncCategory() {

    }
}
