package com.gpnews.consumer.shiro.realm;

import com.gpnews.pojo.User;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author HuangChongHeng
 * @date 2019/12/18
 */
public class UserLoginToken extends UsernamePasswordToken {
    private static final long serialVersionUID = 1L;
    private User user;


    public UserLoginToken(String username, char[] password, boolean rememberMe, String host, User user) {
        super(username, password, rememberMe, host);
        this.user = user;
    }

    public UserLoginToken(String username, String password, User user) {
        super(username, password);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
