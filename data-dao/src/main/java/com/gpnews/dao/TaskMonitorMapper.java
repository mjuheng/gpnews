package com.gpnews.dao;

import com.gpnews.pojo.TaskMonitor;
import com.gpnews.pojo.vo.TaskMonitorVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TaskMonitorMapper extends Mapper<TaskMonitor> {
    List<TaskMonitorVo> page(@Param("name") String name,
                             @Param("start") Integer start,
                             @Param("rows") Integer rows);

    Integer count(@Param("name") String name);
}
