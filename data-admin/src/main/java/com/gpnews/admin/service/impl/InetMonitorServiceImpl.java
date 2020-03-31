package com.gpnews.admin.service.impl;

import com.gpnews.admin.service.InetArticleService;
import com.gpnews.admin.service.InetMonitorService;
import com.gpnews.dao.InetArticleMapper;
import com.gpnews.dao.InetMonitorMapper;
import com.gpnews.pojo.InetArticle;
import com.gpnews.pojo.InetMonitor;
import com.gpnews.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/3/29
 */
@Service
public class InetMonitorServiceImpl extends BaseServiceImpl<InetMonitor> implements InetMonitorService {

    @Autowired
    private InetMonitorMapper mapper;
    @Override
    public Mapper<InetMonitor> getMapper() {
        return mapper;
    }

}
