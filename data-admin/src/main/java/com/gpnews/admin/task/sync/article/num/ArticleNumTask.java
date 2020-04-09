package com.gpnews.admin.task.sync.article.num;

import com.gpnews.admin.enums.TaskInfoName;
import com.gpnews.admin.service.TaskInfoService;
import com.gpnews.dao.ArticleMapper;
import com.gpnews.pojo.Article;
import com.gpnews.pojo.TaskInfo;
import com.gpnews.utils.JsonUtil;
import com.gpnews.utils.RedisUtil;
import com.gpnews.utils.ThreadPoolUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

/**
 * @author HuangChongHeng
 * @date 2020/4/4
 */
@Component
public class ArticleNumTask extends Thread {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private ArticleNumMonitor monitor;
    @Value("${redisData.articleMap}")
    private String articleMap;
    @Autowired
    private TaskInfoService taskInfoService;

    @Override
    public void run() {
        TaskInfo taskInfo = taskInfoService.load(TaskInfoName.syncArticle.getId());
        if (taskInfo.getIsStop()){
            return;
        }
        taskInfo.setStatus(1);
        taskInfoService.updateNoNull(taskInfo);

        ThreadPoolUtil.instance().execute(monitor);
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        ArticleMapper mapper = sqlSession.getMapper(ArticleMapper.class);

        Set<Object> fields = redisUtil.hmFields(articleMap);
        Article article;
        Object obj;
        int count = 0;
        for (Object fieldObj : fields) {
            String field = (String)fieldObj;
            obj = redisUtil.hmGet(articleMap, field);
            HashMap map = JsonUtil.deserialize(obj.toString(), HashMap.class);
            article = new Article();
            article.setId(field);
            article.setReadNum((Integer) map.get("readNum"));
            article.setCommentNum((Integer) map.get("commentNum"));
            mapper.updateByPrimaryKeySelective(article);
            if (++count % 50 == 0){
                sqlSession.flushStatements();
                count = 0;
            }
        }
        sqlSession.flushStatements();

        try {
            monitor.setStop(true);
            monitor.getLock().lock();
            monitor.getCondition().signalAll();
        }finally {
            monitor.getLock().unlock();
        }
        taskInfo.setStatus(0);
        taskInfoService.updateNoNull(taskInfo);
    }
}
