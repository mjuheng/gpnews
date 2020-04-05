package com.gpnews.admin.service.impl;

import com.gpnews.admin.service.TaskMonitorService;
import com.gpnews.dao.TaskMonitorMapper;
import com.gpnews.pojo.TaskMonitor;
import com.gpnews.pojo.vo.TaskMonitorVo;
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
public class TaskMonitorServiceImpl extends BaseServiceImpl<TaskMonitor> implements TaskMonitorService {

    @Autowired
    private TaskMonitorMapper mapper;
    @Override
    public Mapper<TaskMonitor> getMapper() {
        return mapper;
    }


    @Override
    public List<TaskMonitorVo> page(String name, Integer currPage, Integer rows) {
        int start = PageUtil.getStart(currPage, rows);
        return mapper.page(name, start, rows);
    }

    @Override
    public Integer count(String name) {
        return mapper.count(name);
    }
}
