package com.gpnews.publish.service;

import com.gpnews.pojo.Material;

import java.util.List;

public interface MaterialService extends BaseService<Material> {

    List<Material> query(Material material, Integer currPage, Integer rows);
    Integer count(Material material);
}
