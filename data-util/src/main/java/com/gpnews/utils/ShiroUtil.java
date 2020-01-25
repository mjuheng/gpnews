package com.gpnews.utils;

import com.gpnews.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;


/**
 * @author HuangChongHeng
 * @date 2020/1/19
 */
public class ShiroUtil {
    @RequiresAuthentication
    public static String getCurrUserId(){
        return (String) SecurityUtils.getSubject().getSession().getAttribute("id");
    }
}
