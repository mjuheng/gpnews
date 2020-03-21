package com.gpnews.admin.service.impl;

import com.gpnews.admin.service.SysLogService;
import com.gpnews.dao.SysLogMapper;
import com.gpnews.pojo.SysLog;
import com.gpnews.pojo.vo.SyslogVo;
import com.gpnews.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/3/17
 */
@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLog> implements SysLogService {

    @Autowired
    private SysLogMapper mapper;

    @Override
    public Mapper<SysLog> getMapper() {
        return mapper;
    }

    @Override
    public List<SyslogVo> page(String username, String opt, Integer currPage, Integer rows) {
        int start = PageUtil.getStart(currPage, rows);
        return mapper.page(username, opt, start, rows);
    }

    @Override
    public Integer count(String username, String opt) {
        return mapper.count(username, opt);
    }
}
