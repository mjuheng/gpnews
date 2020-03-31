package com.gpnews.admin.task.crawler.wy;

import com.gpnews.admin.annotation.SystemLog;
import com.gpnews.admin.service.InetArticleService;
import com.gpnews.admin.task.crawler.config.SpiderMonitorCustom;
import com.gpnews.admin.task.crawler.config.SpiderStatusCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.monitor.SpiderStatus;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.management.JMException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author HuangChongHeng
 * @date 2020/3/28
 */
@Component
public class WyNewsTask implements Runnable{

    @Autowired
    private WyNewsPipeline pipeline;
    @Autowired
    private WyNewsPageProcessor processor;
    @Autowired
    private InetArticleService service;
    @Autowired
    private WyMonitor monitor;

    private String url = "http://news.163.com/special/0001220O/news_json.js";
//    private String url = "https://www.baidu.com/";

    @Override
    public void run() {

        Spider spider = Spider.create(processor)
                .addUrl(url)
                .addPipeline(pipeline)
                .setExitWhenComplete(true)
                .thread(10);
        try {
            SpiderMonitorCustom.instance().register(spider);
            monitor.setSpider(spider);
            monitor.setStartPage(service.count(1, null));
            monitor.start();
        } catch (JMException e) {
            e.printStackTrace();
        }
        spider.run();
        try {
            monitor.getLock().lock();
            monitor.getCondition().signalAll();
        }finally {
            monitor.getLock().unlock();
        }



    }


}
