package com.gpnews.admin.shiro.service;

import org.apache.shiro.session.Session;

import java.util.List;
import java.util.Map;

public interface ShiroService {
    Map<String, Object> pageSession(String username, Integer currPage, Integer rows);

}
