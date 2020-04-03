package com.gpnews.admin.service.impl;

import com.gpnews.admin.service.MsgService;
import com.gpnews.dao.MsgMapper;
import com.gpnews.pojo.Msg;
import com.gpnews.pojo.vo.MsgVo;
import com.gpnews.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<MsgVo> page(Msg msg, Integer currPage, Integer rows) {
        int start = PageUtil.getStart(currPage, rows);
        return mapper.page(msg, start, rows);
    }

    @Override
    public Integer count(Msg msg) {
        return mapper.count(msg);
    }

    @Override
    public boolean updateRead(String id, Boolean read) {
        String[] ids = id.split(",");
        List<Msg> list = new ArrayList<>();
        for (String s : ids) {
            list.add(new Msg(s, read));
        }
        return updateBatch(list);
    }
}
