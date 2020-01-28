package com.gpnews.publish.service;

import com.gpnews.pojo.Fan;
import com.gpnews.pojo.User;
import com.gpnews.pojo.vo.FanVo;

import java.util.List;

public interface FanService extends BaseService<Fan> {
    List<Fan> page(Fan fan, Integer currPage, Integer rows);

    List<FanVo> pageFans(String id, Integer currPage, Integer rows);

    Integer countFans(String id);
}
