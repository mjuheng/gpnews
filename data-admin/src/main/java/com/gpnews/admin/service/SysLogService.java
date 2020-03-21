package com.gpnews.admin.service;

import com.gpnews.pojo.SysLog;
import com.gpnews.pojo.vo.SyslogVo;

import java.util.List;

public interface SysLogService extends BaseService<SysLog> {
    List<SyslogVo> page(String username, String opt, Integer currPage, Integer rows);

    Integer count(String username, String opt);
}
