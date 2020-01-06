package com.gpnews.admin.shiro.realm;

import com.news.utils.result.ResultUtil;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author HuangChongHeng
 * @date 2020/1/1
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        ResultUtil.writeResponse((HttpServletResponse)response, "401", "用户未登录");
        return false;
    }
}
