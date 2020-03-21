package com.gpnews.admin.service;

import com.gpnews.pojo.Dynamic;
import com.gpnews.pojo.vo.DynamicVo;

import java.util.List;

public interface DynamicService extends BaseService<Dynamic> {
    List<DynamicVo> page(String userId, Integer currPage, Integer rows);

    Integer count(String userId);
}
