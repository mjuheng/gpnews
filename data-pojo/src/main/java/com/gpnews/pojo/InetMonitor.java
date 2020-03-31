package com.gpnews.pojo;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author HuangChongHeng
 * @date 2020/3/30
 */
@Table(name = "inet_monitor")
@Data
public class InetMonitor extends BaseEntity{
    private Date startTime;
    private Date endTime;
    private Integer pageNum;
    private Integer errorNum;
    private Integer newsNum;
    private Integer type;
}
