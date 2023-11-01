package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.product.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaozhen
 * @date 2023/10/31
 */
@Tag(name="品牌接口管理")
@RestController
@RequestMapping("/api/product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @Operation(summary = "查询所有品牌")
    @GetMapping("/findAll")
    public Result findAll() {
        return Result.build(brandService.findAll(), ResultCodeEnum.SUCCESS);
    }
}
