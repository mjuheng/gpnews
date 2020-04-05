package com.gpnews.dao;

import com.gpnews.pojo.TaskInfo;
import com.gpnews.pojo.TaskMonitor;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TaskInfoMapper extends Mapper<TaskInfo> {
}
