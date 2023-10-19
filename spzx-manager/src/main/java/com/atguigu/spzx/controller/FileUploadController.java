package com.atguigu.spzx.controller;

import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiaozhen
 * @date 2023/10/17
 */
@Tag(name="文件上传接口")
@RestController
@RequestMapping("/admin/system")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @Operation(summary = "文件上传")
    @PostMapping("/fileUpload")
    public Result fileUpload(@RequestParam MultipartFile file) {
        String url = fileUploadService.fileUpload(file);
        return Result.build(url, ResultCodeEnum.SUCCESS);
    }
}
