package com.atguigu.spzx.controller;

import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.service.ProductSpecService;
import com.github.pagehelper.PageInfo;
import com.google.errorprone.annotations.Var;
import io.swagger.v3.oas.annotations.Operation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/22
 */
@RestController
@RequestMapping("/admin/product/productSpec")
public class ProductSpecController {
    @Autowired
    private ProductSpecService productSpecService;

    @Operation(summary = "商品规格查询")
    @GetMapping("/{page}/{limit}")
    public Result queryPage(@PathVariable Integer page,
                            @PathVariable Integer limit) {
        PageInfo<ProductSpec> list = productSpecService.queryPage(page, limit);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "商品规格添加")
    @PostMapping("/save")
    public Result save(@RequestBody ProductSpec productSpec) {
        productSpecService.save(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "商品规格修改")
    @PutMapping("/updateById")
    public Result updateById(@RequestBody ProductSpec productSpec) {
        productSpecService.updateById(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "商品规格删除")
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        productSpecService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询所有规格")
    @GetMapping("/findAll")
    public Result findALl() {
       List<ProductSpec> list = productSpecService.findAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
