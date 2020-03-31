package com.gpnews.admin.task;

import com.gpnews.admin.task.crawler.wy.WyNewsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author HuangChongHeng
 * @date 2020/3/29
 */
@Component
public class CrawlerScheduling implements SchedulingConfigurer {

//    private String wyCrawlerCron = "0 0 0 */1 * ?";
    private String wyCrawlerCron = "15 57 13 */1 * ?";

    @Autowired
    private WyNewsTask wyNewsTask;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(wyNewsTask, triggerContext -> {
            // 任务触发，可修改任务的执行周期
            CronTrigger trigger = new CronTrigger(wyCrawlerCron);
            Date nextExec = trigger.nextExecutionTime(triggerContext);
            return nextExec;
        });  //加入时间
    }


    public void setWyCrawlerCron(String wyCrawlerCron) {
        this.wyCrawlerCron = wyCrawlerCron;
    }
}
