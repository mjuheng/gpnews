package com.gpnews.file.controller;

import com.gpnews.file.UploadServiceImpl;
import com.gpnews.file.service.UploadService;
import com.gpnews.utils.result.CommonResult;
import com.gpnews.utils.result.ResultUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HuangChongHeng
 * @date 2020/1/19
 */

@RestController
@RequestMapping("/image")
public class ImageController {

    @Resource
    private UploadService uploadServiceImpl;

    private String[] allowImageTypes = {"jpeg", "jpg", "png"};

    @RequestMapping("/upload")
    public CommonResult upload(@RequestParam("image") MultipartFile image, HttpServletRequest request){
        // 判断文件是否为空
        if (image == null || image.getSize()  <= 0){
            return ResultUtil.errorSingleResult(false, "文件为空，上传失败");
        }
        // 判断文件格式
        String originalFilename = image.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String uploadType = originalFilename.substring(index + 1);
        if (!Arrays.asList(allowImageTypes).contains(uploadType)){
            return ResultUtil.errorSingleResult(false, "仅支持" + Arrays.toString(allowImageTypes) + "的图片格式");
        }
        // 保存图片
        String fileName = uploadServiceImpl.uploadFile(image, UploadServiceImpl.FILE_TYPE_IMAGE);
        if (fileName == null){
            return ResultUtil.errorSingleResult(false);
        }
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();
            int port = request.getLocalPort();
            fileName = "https://" + ip + ":" + port + "/image/" + fileName;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return ResultUtil.successSingleResult(fileName);
    }
}
