package com.gpnews.admin.task.crawler.wy;

import com.gpnews.admin.enums.TaskInfoName;
import com.gpnews.admin.service.InetArticleService;
import com.gpnews.admin.service.TaskInfoService;
import com.gpnews.admin.service.TaskMonitorService;
import com.gpnews.admin.task.crawler.config.SpiderMonitorCustom;
import com.gpnews.admin.task.crawler.config.SpiderStatusCustom;
import com.gpnews.pojo.TaskMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author HuangChongHeng
 * @date 2020/3/30
 */
@Component
public class WyMonitor extends Thread{

    @Autowired
    private TaskMonitorService service;
    @Autowired
    private InetArticleService inetArticleService;
    @Autowired
    private TaskInfoService taskInfoService;

    private Spider spider;
    private Integer startPage;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    @Override
    public void run() {
        TaskMonitor monitor = new TaskMonitor();
        monitor.setStartTime(new Date());

        SpiderStatusCustom spiderStatus = SpiderMonitorCustom.instance().getSpiderStatuses().get(spider.getUUID());
        if (spider != null) {
            // 等待爬虫结束
            do  {
                try {
                    lock.lock();
                    condition.await(60, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }while (!"Stopped".equals(spiderStatus.getStatus()));

            // 插入参数
            monitor.setTaskInfoId(TaskInfoName.wy.getId());
            monitor.setEndTime(new Date());
            String desc = "访问总数：" + spiderStatus.getTotalPageCount() +
                          ";\n页面出错数：" + spiderStatus.getErrorPageCount() +
                          ";\n新增数据数：" + (inetArticleService.count(1, null) - this.startPage);
            monitor.setDescription(desc);
            service.insert(monitor);
        }
    }

    public void setSpider(Spider spider) {
        this.spider = spider;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Condition getCondition() {
        return condition;
    }

    public Lock getLock() {
        return lock;
    }
}
