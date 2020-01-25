package com.gpnews.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author HuangChongHeng
 * @date 2020/1/19
 */
@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

    @Value("${file-upload.base}")
    private String fileUploadBase;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("file:" + fileUploadBase + "\\image");
    }
}
