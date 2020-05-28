package com.gpnews.admin.service;

import com.gpnews.pojo.Msg;
import com.gpnews.pojo.vo.MsgVo;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/4/1
 */
public interface MsgService extends BaseService<Msg>{

    List<MsgVo> page(Msg msg, Integer currPage, Integer rows);

    Integer count(Msg msg);

    boolean updateRead(String id, Boolean read);

}
