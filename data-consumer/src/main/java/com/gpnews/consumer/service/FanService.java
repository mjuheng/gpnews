package com.gpnews.consumer.service;

import com.gpnews.pojo.Fan;
import com.gpnews.pojo.vo.FanVo;

import java.util.List;

public interface FanService extends BaseService<Fan> {
    List<Fan> page(Fan fan, Integer currPage, Integer rows);

    List<FanVo> pageFans(String id, Integer currPage, Integer rows);

    Integer countFans(String id);

    int delByBothUserId(String fromUserId, String toUserId);

    boolean isFan(String fromUserId, String toUserId);
}
