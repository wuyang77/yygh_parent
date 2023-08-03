package com.wuyang.yygh.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 上传文件
     */
    public String uploadFile(MultipartFile file);
}
