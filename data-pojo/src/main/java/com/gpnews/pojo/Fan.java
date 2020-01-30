package com.gpnews.pojo;

import lombok.Data;

import javax.persistence.Table;

/**
 * @author HuangChongHeng
 * @date 2020/1/27
 */
@Data
@Table(name = "u_fan")
public class Fan extends BaseEntity {
    private String fromUserId;
    private String toUserId;

    public Fan(){}

    public Fan(String fromUserId, String toUserId){
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }
}
