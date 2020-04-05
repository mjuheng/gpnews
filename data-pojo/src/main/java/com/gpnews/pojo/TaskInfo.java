package com.gpnews.pojo;

import lombok.Data;

import javax.persistence.Table;

/**
 * @author HuangChongHeng
 * @date 2020/4/4
 */
@Data
@Table(name = "task_info")
public class TaskInfo extends BaseEntity {

    private String name;
    private String cron;
    private Integer status;   // 1.运行     2. 停止
    private Boolean isStop;
}
