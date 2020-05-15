package com.gpnews.admin.shiro.service.impl;

import com.gpnews.admin.shiro.service.ShiroService;
import com.gpnews.pojo.Permission;
import com.gpnews.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author HuangChongHeng
 * @date 2020/2/8
 */
@Service
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    private SessionDAO sessionDAO;

    @Override
    public Map<String, Object> pageSession(String username, Integer currPage, Integer rows) {
        int start = PageUtil.getStart(currPage, rows);
        List<Session> retList = new ArrayList<>();
        List<Session> onlineUser = new ArrayList<>(sessionDAO.getActiveSessions());

        // 根据用户名，存放进map中
        Map<String, ArrayList<Session>> map = onlineUser.stream().collect(Collectors.toMap(x -> (String)x.getAttribute("username"), x -> new ArrayList<Session>() {{
            add(x);
        }}, (k1, k2) -> {
            k1.addAll(k2);
            return k1;
        }));

        // 条件过滤
        if (StringUtils.isBlank(username)){
            retList.addAll(onlineUser);
        }else {
            Set<String> keys = map.keySet();
            for (String key: keys){
                if (key.contains(username)){
                    retList.addAll(map.get(key));
                }
            }
        }
        Map<String, Object> ret = new HashMap<>();
        ret.put("count", retList.size());

        // 分页过滤
        if (currPage != null && rows != null) {
            try {
                retList = retList.subList(start, start + rows);
            }catch (IndexOutOfBoundsException e){
                retList = retList.subList(start, retList.size());
            }
        }
        ret.put("list", retList);
        return ret;
    }
}
