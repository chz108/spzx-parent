package com.atguigu.spzx.controller;

import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.service.ProductUnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/22
 */
@Tag(name = "商品单位")
@RestController
@RequestMapping("/admin/product/productUnit")
public class ProductUnitController {
    @Autowired
    private ProductUnitService productUnitService;

    @Operation(summary = "查询所有商品单位")
    @GetMapping("/findAll")
    public Result findALl() {
        List<ProductUnit> list = productUnitService.findAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
