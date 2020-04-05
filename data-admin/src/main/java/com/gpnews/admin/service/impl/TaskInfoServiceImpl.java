package com.gpnews.admin.service.impl;

import com.gpnews.admin.service.TaskInfoService;
import com.gpnews.admin.task.crawler.wy.WyNewsTask;
import com.gpnews.admin.task.sync.article.num.ArticleNumTask;
import com.gpnews.dao.TaskInfoMapper;
import com.gpnews.pojo.TaskInfo;
import com.gpnews.utils.ThreadPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author HuangChongHeng
 * @date 2020/4/4
 */
@Service
public class TaskInfoServiceImpl extends BaseServiceImpl<TaskInfo> implements TaskInfoService {

    @Autowired
    private TaskInfoMapper mapper;
    @Autowired
    @Lazy
    private WyNewsTask wyNewsTask;
    @Autowired
    @Lazy
    private ArticleNumTask articleNumTask;

    @Override
    public Mapper<TaskInfo> getMapper() {
        return mapper;
    }

    @Override
    public void runTask(String id) {
        if ("1".equals(id)){
            ThreadPoolUtil.instance().execute(wyNewsTask);
        } else if ("2".equals(id)){
            ThreadPoolUtil.instance().execute(articleNumTask);
        }
    }
}
