package com.gpnews.admin.service;

import com.gpnews.pojo.Visits;

import java.util.List;

public interface VisitsService extends BaseService<Visits> {
    /**
     * 查询最近的访问量
     * @param day   最近的天数
     * @return
     */
    List<Integer> selectVisits(String userId, int day);

}
