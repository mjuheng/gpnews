package com.gpnews.publish.controller;

import com.news.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HuangChongHeng
 * @date 2020/1/1
 */
@Controller
public class DemoController {


    @ResponseBody
    @RequestMapping("/demo")
    public Map<String, Object> demo(){
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        User user = new User();
        user.setUsername("haha");
        map.put("data", user);
        return map;
    }
}
