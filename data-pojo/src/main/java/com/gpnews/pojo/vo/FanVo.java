package com.gpnews.pojo.vo;

import com.gpnews.pojo.Fan;
import com.gpnews.pojo.User;
import lombok.Data;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/1/27
 */
@Data
public class FanVo extends User {
    private Boolean isFan;
}
