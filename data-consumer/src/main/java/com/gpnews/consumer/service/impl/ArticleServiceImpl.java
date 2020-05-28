package com.gpnews.consumer.service.impl;

import com.gpnews.consumer.service.ArticleService;
import com.gpnews.dao.ArticleMapper;
import com.gpnews.pojo.Article;
import com.gpnews.pojo.vo.ArticleVo;
import com.gpnews.utils.JsonUtil;
import com.gpnews.utils.PageUtil;
import com.gpnews.utils.RedisUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${redisData.articleMap}")
    private String articleMap;

    @Override
    public Mapper<Article> getMapper() {
        return articleMapper;
    }

    @Override
    public List<ArticleVo> pageExclContent(ArticleVo article, String beginPubTime, String endPubTime, Integer currPage, Integer rows) {

        Integer start = PageUtil.getStart(currPage, rows);
        return articleMapper.pageExclContent(article,beginPubTime, endPubTime, start, rows);
    }

    @Override
    public Integer count(ArticleVo article, String beginPubTime, String endPubTime) {
        return articleMapper.count(article, beginPubTime, endPubTime);
    }

    @Override
    public ArticleVo getById(String id) {
        return articleMapper.getById(id);
    }

    @Override
    public List<ArticleVo> pageMP(ArticleVo article, String pubTime, Integer rows) {
        return articleMapper.pageMP(article, pubTime, rows);
    }

    @Override
    public List<ArticleVo> pageExclContentByFan(ArticleVo article, String beginPubTime, String endPubTime, String userId, Integer currPage, Integer rows) {

        Integer start = PageUtil.getStart(currPage, rows);
        return articleMapper.pageByFan(article,beginPubTime, endPubTime, userId, start, rows);
    }

    @Override
    public Integer countByFan(ArticleVo article, String userId, String beginPubTime, String endPubTime) {
        return articleMapper.countByFan(article, userId, beginPubTime, endPubTime);
    }

    @Override
    public void addNum(String id, Integer type) {
        Map<String, Object> map;
        try {
            map = countReadAndCount(id);
        }catch (NullPointerException e){ return; }
        if (type == 1){
            int readNum = Integer.parseInt(map.get("readNum").toString());
            map.put("readNum", readNum+1);

        }else if (type == 2){
            int commentNum =Integer.parseInt(map.get("commentNum").toString());
            map.put("commentNum", commentNum+1);
        }
        redisUtil.hmSet(articleMap, id, JsonUtil.serialize(map));
    }

    @Override
    public Map<String, Object> countReadAndCount(String id) {
        Object obj = redisUtil.hmGet(articleMap, id);
        if (obj == null) {
            Article article = load(id);
            Map<String, Object> map = new HashMap<>();
            map.put("readNum", article.getReadNum());
            map.put("commentNum", article.getCommentNum());
            obj = JsonUtil.serialize(map);
            redisUtil.hmSet(articleMap, id, obj);
        }

        return JsonUtil.deserialize(obj.toString(), HashMap.class);
    }
}
