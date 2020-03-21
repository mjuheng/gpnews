package com.gpnews.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author HuangChongHeng
 * @date 2020/3/17
 */
@Data
@Table(name = "sys_log")
public class SysLog extends BaseEntity{

    private String userId;

    private String operation;

    private Integer time;

    private String method;

    private String params;

    private String ip;

}
