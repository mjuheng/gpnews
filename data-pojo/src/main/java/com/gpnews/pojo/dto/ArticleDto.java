package com.gpnews.pojo.dto;

import com.gpnews.pojo.Article;
import lombok.Data;

/**
 * @author HuangChongHeng
 * @date 2020/1/19
 */
@Data
public class ArticleDto extends Article {
    private Boolean draft;
}
