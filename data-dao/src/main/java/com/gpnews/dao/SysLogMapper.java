package com.gpnews.dao;

import com.gpnews.pojo.SysLog;
import com.gpnews.pojo.vo.SyslogVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/3/17
 */
public interface SysLogMapper extends Mapper<SysLog> {
    List<SyslogVo> page(@Param("username") String username,
                        @Param("opt") String opt,
                        @Param("start") Integer start,
                        @Param("rows") Integer rows);

    Integer count(@Param("username") String username,
                  @Param("opt") String opt);
}
