package com.gpnews.admin.task.sync.article.num;

import com.gpnews.admin.enums.TaskInfoName;
import com.gpnews.admin.service.TaskInfoService;
import com.gpnews.admin.service.TaskMonitorService;
import com.gpnews.admin.task.crawler.config.SpiderMonitorCustom;
import com.gpnews.admin.task.crawler.config.SpiderStatusCustom;
import com.gpnews.pojo.TaskInfo;
import com.gpnews.pojo.TaskMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author HuangChongHeng
 * @date 2020/4/4
 */
@Component
public class ArticleNumMonitor extends Thread{

    @Autowired
    private TaskMonitorService service;

    private Lock lock;
    private Condition condition;
    private boolean stop;

    public ArticleNumMonitor(){
        this.lock = new ReentrantLock();
        this.condition = this.lock.newCondition();
        this.stop = false;
    }


    @Override
    public void run() {
        TaskMonitor monitor = new TaskMonitor();
        monitor.setStartTime(new Date());

        if (!stop) {
            do {
                try {
                    lock.lock();
                    condition.await(60, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            } while (!stop);

            // 插入参数
            monitor.setTaskInfoId(TaskInfoName.syncArticle.getId());

            monitor.setEndTime(new Date());
            service.insert(monitor);
            this.stop = false;
        }
    }

    public Lock getLock() {
        return lock;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
