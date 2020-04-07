package com.gpnews.pojo.dto;

import com.gpnews.pojo.User;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author HuangChongHeng
 * @date 2020/4/6
 */
@Data
public class UserDto extends User {

    @NotNull(message = "验证码不能为空")
    private String verify;
}
