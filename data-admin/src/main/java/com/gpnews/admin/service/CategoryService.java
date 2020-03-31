package com.gpnews.admin.service;

import com.gpnews.pojo.Category;

import java.util.List;

public interface CategoryService extends BaseService<Category> {
    List<Category> page(Integer currPage, Integer rows);
}
