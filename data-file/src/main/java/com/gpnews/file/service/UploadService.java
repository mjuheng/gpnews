package com.gpnews.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    /**
     * 上传文件
     * @param file
     * @param file_type
     * @return
     */
    String uploadFile(MultipartFile file, String file_type);
}
