package com.gpnews.file;

import com.gpnews.file.service.UploadService;
import com.gpnews.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author HuangChongHeng
 * @date 2020/1/19
 */
@Service
public class UploadServiceImpl implements UploadService {

    public static final String FILE_TYPE_IMAGE = "image";

    @Value("${file-upload.base}")
    private String fileUploadBase;

    @Override
    public String uploadFile(MultipartFile fileData, String file_type) {

        // 设置文件名
        String originalFileName = fileData.getOriginalFilename();
        String fileName = UUIDUtil.create();
        fileName += originalFileName.substring(originalFileName.lastIndexOf("."));

        // 图片上传路径
        String filePath = fileUploadBase + "//" + file_type;
        File file = new File(filePath + "//" + fileName);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }

        // 保存文件
        try {
            fileData.transferTo(file);
        } catch (IOException e) {
            return null;
        }

        return fileName;
    }
}
