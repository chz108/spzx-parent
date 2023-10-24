package com.atguigu.spzx.controller;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.service.ProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaozhen
 * @date 2023/10/22
 */
@Tag(name = "商品管理")
@RestController
@RequestMapping("/admin/product/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(summary = "商品管理条件分页查询")
    @GetMapping("/{page}/{limit}")
    public Result queryPage(@PathVariable Integer page,
                            @PathVariable Integer limit,
                            ProductDto productDto) {
        PageInfo<Product> list = productService.queryPage(page, limit, productDto);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "商品管理添加")
    @PostMapping("/save")
    public Result save(@RequestBody Product product) {
        productService.save(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "商品信息回显")
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return Result.build(product, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改商品信息")
    @PutMapping("/updateById")
    public Result updateById(@RequestBody Product product) {
        productService.updateById(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除商品信息")
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id) {
        productService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "商品审核")
    @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
    public Result updateAuditStatus(@PathVariable Long id,
                             @PathVariable Integer auditStatus) {
        productService.updateAuditStatus(id,auditStatus);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "商品上下架")
    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id,
                                    @PathVariable Integer status) {
        productService.updateStatus(id,status);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
