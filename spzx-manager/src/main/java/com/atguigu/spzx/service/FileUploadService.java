package com.atguigu.spzx.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiaozhen
 * @date 2023/10/17
 */
public interface FileUploadService {
    String fileUpload(MultipartFile file);
}
