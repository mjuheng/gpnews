package com.gpnews.admin.service;

import com.gpnews.pojo.TaskMonitor;
import com.gpnews.pojo.vo.TaskMonitorVo;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/3/29
 */
public interface TaskMonitorService extends BaseService<TaskMonitor>{

    List<TaskMonitorVo> page(String name, Integer currPage, Integer rows);

    Integer count(String name);
}
