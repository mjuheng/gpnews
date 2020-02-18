package com.gpnews.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * @author HuangChongHeng
 * @date 2020/2/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "a_favorites")
public class Favorites extends BaseEntity{
    private String userId;
    private String articleId;
}
