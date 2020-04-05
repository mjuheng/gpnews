package com.gpnews.pojo;

import lombok.Data;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author HuangChongHeng
 * @date 2020/1/20
 */
@Data
@Table(name = "s_material")
public class Material extends BaseEntity {
    private String userId;
    @NotBlank(message = "图片不能为空")
    private String imageUrl;
    public Material(String userId){
        this.userId = userId;
    }
    public Material(){}
}
