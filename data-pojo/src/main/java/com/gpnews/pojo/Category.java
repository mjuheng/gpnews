package com.gpnews.pojo;

import lombok.Data;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author HuangChongHeng
 * @date 2020/1/17
 */
@Data
@Table(name = "a_category")
public class Category extends BaseEntity {
    @NotNull(message = "分类名不能为空")
    private String name;
}
