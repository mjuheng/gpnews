package com.gpnews.admin.service;

import com.gpnews.pojo.TaskInfo;

public interface TaskInfoService extends BaseService<TaskInfo> {
    void runTask(String id);
}
