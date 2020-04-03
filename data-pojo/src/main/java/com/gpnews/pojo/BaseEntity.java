package com.gpnews.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author HuangChongHeng
 * @date 2019/12/19
 */
@Data
public class BaseEntity {
    @Id
    private String id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm", timezone = "GMT+8")
    private Date createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm", timezone = "GMT+8")
    private Date modifiedTime;
}
