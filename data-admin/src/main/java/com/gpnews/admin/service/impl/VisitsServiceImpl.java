package com.gpnews.admin.service.impl;

import com.gpnews.admin.service.VisitsService;
import com.gpnews.dao.VisitsMapper;
import com.gpnews.pojo.Visits;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
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

    @Override
    public List<Integer> selectVisits(String userId, int day) {
        Instant instant = Instant.now();
        instant = instant.minus(Duration.ofDays(day));
        Date since = Date.from(instant);

        List<Integer> ret = new ArrayList<>();
        List<Visits> visits =  mapper.selectSinceTime(userId, since);

        // 填入访问量数据
        Date time = (Date) since.clone();
        int index = 0;
        for (int i = 0; i <= day; i++){
            if (index < visits.size() && DateUtils.isSameDay(time, visits.get(index).getTime())){
                ret.add(visits.get(index).getNum());
                index++;
            }else{
                ret.add(0);
            }
            time = Date.from(time.toInstant().plus(Duration.ofDays(1)));    // 日期加一天
        }

        return ret;
    }



    @Override
    public Mapper<Visits> getMapper() {
        return mapper;
    }
}
