package com.gpnews.publish.service.impl;

import com.gpnews.dao.FanMapper;
import com.gpnews.pojo.Fan;
import com.gpnews.pojo.User;
import com.gpnews.pojo.vo.FanVo;
import com.gpnews.publish.service.FanService;
import com.gpnews.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/27
 */
@Service
public class FanServiceImpl extends BaseServiceImpl<Fan> implements FanService {

    @Autowired
    private FanMapper mapper;

    @Override
    public Mapper<Fan> getMapper() {
        return mapper;
    }

    @Override
    public List<Fan> page(Fan fan, Integer currPage, Integer rows) {
        int start = PageUtil.getStart(currPage, rows);
        return mapper.page(fan, start, rows);
    }

    @Override
    public List<FanVo> pageFans(String id, Integer currPage, Integer rows) {
        int start = PageUtil.getStart(currPage, rows);
        List<FanVo> fanVos = mapper.pageFans(id, start, rows);
        List<Fan> fans = mapper.page(new Fan(id, null), start, rows);
        // 判断是否互相关注
        int i = 0, j = 0;
        fans:
        for (; i < fans.size(); i++){
            for (; j < fanVos.size(); j++){
                if (fans.get(i).getToUserId().equals(fanVos.get(j).getId())){
                    fanVos.get(j).setIsFan(true);
                    continue fans;
                }
            }
            break;
        }
        return fanVos;
    }

    @Override
    public Integer countFans(String id) {
        return mapper.countFans(id);
    }

    @Override
    public int delByBothUserId(String fromUserId, String toUserId) {
        return mapper.delByBothUserId(fromUserId, toUserId);
    }
}
