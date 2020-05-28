package com.gpnews.admin.service;

import com.gpnews.pojo.Visits;

import java.util.List;

public interface VisitsService extends BaseService<Visits> {

    List<Visits> selectByTime(String userId, Integer type);

    void addPublish(String articleId);
}
