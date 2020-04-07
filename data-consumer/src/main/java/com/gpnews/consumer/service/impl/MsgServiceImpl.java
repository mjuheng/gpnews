package com.gpnews.consumer.service.impl;

import com.gpnews.consumer.service.MsgService;
import com.gpnews.dao.MsgMapper;
import com.gpnews.pojo.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;


/**
 * @author HuangChongHeng
 * @date 2020/4/1
 */
@Service
public class MsgServiceImpl extends BaseServiceImpl<Msg> implements MsgService {

    @Autowired
    private MsgMapper mapper;

    @Override
    public Mapper<Msg> getMapper() {
        return mapper;
    }

}
