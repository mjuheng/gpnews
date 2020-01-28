package com.gpnews.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
@Table(name = "a_article")
@Data
public class Article extends BaseEntity {
    @NotBlank(message = "标题不能为空")
    private String title;       // 标题

    private String headImage;   // 封面

    @NotBlank(message = "内容不能为空")
    private String content;     // 内容
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm", timezone = "GMT+8")

    private Date publishTime;   // 发布时间

    private String userId;      // 发布人

    private Integer commentNum; // 回复量

    private Integer readNum;    // 阅读量

    @NotBlank(message = "分类不能为空")
    private String categoryId;  // 分类

    private Integer status;     // 状态

    private Boolean isComment;
}
