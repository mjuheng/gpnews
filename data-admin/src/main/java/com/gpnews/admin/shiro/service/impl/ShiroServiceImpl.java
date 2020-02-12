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
        Map<String, List<Session>> map = listToMap(onlineUser);
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

    private Map<String, List<Session>> listToMap(List<Session> sessionList) {
        Map<String, List<Session>> map = new HashMap<>();
        for (Session session : sessionList) {
            List<Session> sessions = map.get((String) session.getAttribute("username"));
            if (sessions != null && sessions.size() > 0) {
                sessions.add(session);
            } else {
                List<Session> list = new ArrayList<>();
                list.add(session);
                map.put((String) session.getAttribute("username"), list);
            }
        }
        return map;
    }
}
