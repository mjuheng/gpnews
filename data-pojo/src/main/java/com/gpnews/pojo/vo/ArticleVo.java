package com.gpnews.pojo.vo;

import com.gpnews.pojo.Article;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author HuangChongHeng
 * @date 2020/2/9
 */
@Data
public class ArticleVo extends Article {
    private String username;
    private String categoryName;
}
