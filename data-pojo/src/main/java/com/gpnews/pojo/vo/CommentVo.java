package com.gpnews.pojo.vo;

import com.gpnews.pojo.Comment;
import lombok.Data;

import java.util.List;

/**
 * @author HuangChongHeng
 * @date 2020/2/14
 */
@Data
public class CommentVo extends Comment {
    private String username;
    private String userPhoto;
    private List<CommentVo> children;
}
