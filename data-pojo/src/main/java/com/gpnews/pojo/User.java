package com.gpnews.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    private String password;
    @Length(max = 11, message = "手机号过长")
    private String phone;
    private String email;
    private String photo;
    private String intro;
    private Boolean isLock;


    public User(){};
    public User(String username){
        this.username = username;
    }

}
