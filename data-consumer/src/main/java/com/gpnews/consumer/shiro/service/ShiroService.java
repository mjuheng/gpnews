package com.gpnews.consumer.shiro.service;

import java.util.Map;

public interface ShiroService {
    Map<String, Object> pageSession(String username, Integer currPage, Integer rows);

}
