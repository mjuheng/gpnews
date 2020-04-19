package com.gpnews.admin.task.crawler.wy;

import com.gpnews.admin.service.InetArticleService;
import com.gpnews.pojo.InetArticle;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


/**
 * @author HuangChongHeng
 * @date 2020/3/28
 */
@Component
public class WyNewsPipeline implements Pipeline {

    private final Logger logger = LoggerFactory.getLogger(WyNewsPipeline.class);

    @Autowired
    private InetArticleService service;

    @Override
    public void process(ResultItems resultItems, Task task) {
        if (resultItems.get("title") != null) {
            InetArticle article = new InetArticle();
            article.setContent(resultItems.get("content"));
            article.setUsername(resultItems.get("username"));
            article.setTitle(resultItems.get("title"));
            article.setUrl(resultItems.get("url"));
            article.setPublishTime(resultItems.get("publishTime"));
            article.setType(1);
            service.insert(article);
            logger.error("mybatis publishTime====" + resultItems.get("publishTime") + "----title=========" + resultItems.get("title"));
        }
    }
}
