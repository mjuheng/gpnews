package com.gpnews.admin.task.sync.article;

import com.gpnews.admin.enums.TaskInfoName;
import com.gpnews.admin.service.TaskInfoService;
import com.gpnews.admin.task.sync.article.num.ArticleNumTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author HuangChongHeng
 * @date 2020/4/4
 */
@Component
public class ArticleScheduling implements SchedulingConfigurer {
    private String articleNumCron = "0 0 */1 * * ?";

    @Autowired
    private ArticleNumTask articleNumTask;
    @Autowired
    private TaskInfoService taskInfoService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        articleNumCron = taskInfoService.load(TaskInfoName.syncArticle.getId()).getCron();

        scheduledTaskRegistrar.addTriggerTask(articleNumTask, triggerContext -> {
            // 任务触发，可修改任务的执行周期
            CronTrigger trigger = new CronTrigger(articleNumCron);
            Date nextExec = trigger.nextExecutionTime(triggerContext);
            return nextExec;
        });  //加入时间
    }
}
