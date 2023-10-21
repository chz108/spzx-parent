package com.atguigu.spzx.controller;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.simpleframework.xml.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/20
 */
@RestController
@RequestMapping("/admin/product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "根据parentId获取下级节点")
    @GetMapping(value = "/findByParentId/{parentId}")
    public Result findByParentId(@PathVariable Long parentId) {
        List<Category> list = categoryService.findByParentId(parentId);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "将分类下载成Excel文件")
    @GetMapping(value = "/exportData")
    public void exportData(HttpServletResponse response) {
        categoryService.exportData(response);
    }

    @Operation(summary = "上传Excel文件作为分类")
    @PostMapping(value = "/importData")
    public Result importData(MultipartFile file) {
        categoryService.importData(file);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
