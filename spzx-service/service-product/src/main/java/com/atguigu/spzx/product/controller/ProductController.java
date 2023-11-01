package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.atguigu.spzx.product.service.ProductService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaozhen
 * @date 2023/10/30
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{page}/{limit}")
    public Result queryPage(@PathVariable Integer page,
                            @PathVariable Integer limit,
                            ProductSkuDto productSkuDto) {
        PageInfo<ProductSku> pageInfo = productService.queryPage(page, limit, productSkuDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/item/{skuId}")
    public Result item(@PathVariable Integer skuId) {
        ProductItemVo productItemVo = productService.item(skuId);
        return Result.build(productItemVo, ResultCodeEnum.SUCCESS);
    }

}
