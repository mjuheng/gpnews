package com.gpnews.admin.service.impl;

import com.gpnews.admin.service.VisitsService;
import com.gpnews.dao.ArticleMapper;
import com.gpnews.dao.VisitsMapper;
import com.gpnews.pojo.Visits;
import com.gpnews.pojo.vo.ArticleVo;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/29
 */
@Service
public class VisitsServiceImpl extends BaseServiceImpl<Visits> implements VisitsService {

    @Autowired
    private VisitsMapper mapper;
    @Autowired
    private ArticleMapper articleMapper;

    /**
     *
     * @param userId
     * @param type 1 日  2 周  3 月
     * @return
     */
    @Override
    public List<Visits> selectByTime(String userId, Integer type) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Visits> ret = new ArrayList<>();
        String beginTime = sdf.format(calendar.getTime());
        String endTime = null;

        for (int i = 0; i < 7; i++) {
            endTime = beginTime;
            if (type == 1) {
                calendar.add(Calendar.DATE, -1);
            } else if (type == 2){
                calendar.add(Calendar.WEEK_OF_MONTH, -1);
            } else if (type == 3){
                calendar.add(Calendar.MONTH, -1);
            }
            beginTime = sdf.format(calendar.getTime());
            ret.add(0, mapper.selectByTime(userId, beginTime, endTime));
        }
        return ret;
    }

    @Override
    public void addPublish(String articleId) {
        String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        ArticleVo article = articleMapper.getById(articleId);
        String userId = article.getUserId();
        Visits visits = mapper.selectByBeginTime(userId, date);
        if (visits == null){
            visits = new Visits();
            visits.setPublishNum(1);
            visits.setUserId(userId);
            visits.setTime(new Date());
            insert(visits);
        }else {
            visits.setPublishNum(visits.getPublishNum() + 1);
            updateNoNull(visits);
        }
    }

    @Override
    public Mapper<Visits> getMapper() {
        return mapper;
    }
}
