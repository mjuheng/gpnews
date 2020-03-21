package com.gpnews.pojo.vo;

import com.gpnews.pojo.Dynamic;
import lombok.Data;

/**
 * @author HuangChongHeng
 * @date 2020/3/20
 */
@Data
public class DynamicVo extends Dynamic {
    private String articleTitle;
    private String username;
    private String optUsername;
    private String optUserPhoto;
}
