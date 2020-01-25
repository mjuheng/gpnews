package com.gpnews.publish.service.impl;

import com.gpnews.dao.MaterialMapper;
import com.gpnews.pojo.Material;
import com.gpnews.publish.service.MaterialService;
import com.gpnews.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/20
 */
@Service
public class MaterialServiceImpl extends BaseServiceImpl<Material> implements MaterialService {

    @Autowired
    private MaterialMapper mapper;

    @Override
    public Mapper<Material> getMapper() {
        return mapper;
    }

    @Override
    public List<Material> query(Material material, Integer currPage, Integer rows) {
        int start = PageUtil.getStart(currPage, rows);
        return mapper.query(material , start, rows);
    }

    @Override
    public Integer count(Material material) {
        return mapper.count(material);
    }
}
