package com.gpnews.admin.task.crawler.wy;

import com.gpnews.admin.service.InetArticleService;
import com.gpnews.admin.service.InetMonitorService;
import com.gpnews.admin.task.crawler.config.SpiderMonitorCustom;
import com.gpnews.admin.task.crawler.config.SpiderStatusCustom;
import com.gpnews.pojo.InetMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;

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
    private InetMonitorService service;
    @Autowired
    private InetArticleService inetArticleService;

    private Spider spider;
    private Integer startPage;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    @Override
    public void run() {
        InetMonitor monitor = new InetMonitor();
        monitor.setStartTime(new Date());

        SpiderStatusCustom spiderStatus = SpiderMonitorCustom.instance().getSpiderStatuses().get(spider.getUUID());
        if (spider != null) {
            // 等待爬虫结束
            do  {
                try {
                    lock.lock();
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }while (!"Stopped".equals(spiderStatus.getStatus()));

            // 插入参数
            monitor.setEndTime(new Date());
            monitor.setErrorNum(spiderStatus.getErrorPageCount());
            monitor.setPageNum(spiderStatus.getTotalPageCount());
            monitor.setType(1);
            monitor.setNewsNum(inetArticleService.count(1, null) - this.startPage);
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
