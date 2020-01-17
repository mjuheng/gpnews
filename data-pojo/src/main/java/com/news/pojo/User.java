package com.news.pojo;

import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @author HuangChongHeng
 * @date 2019/12/18
 */
@Data
@Table(name = "u_user")
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String phone;
    private String email;
    private Boolean isLock;
    private Date createdTime;
    private Date modifiedTime;
    @Transient
    private Role[] role;


    public User(){};
    public User(String username){
        this.username = username;
    }

}
