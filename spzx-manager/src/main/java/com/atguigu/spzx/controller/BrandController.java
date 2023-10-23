package com.atguigu.spzx.controller;

import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.service.BrandService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/20
 */
@RestController
@RequestMapping(value = "/admin/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Operation(summary = "分页查询")
    @GetMapping("/{pageNum}/{pageSize}")
    public Result queryPage(@PathVariable Integer pageNum,
                            @PathVariable Integer pageSize) {
        PageInfo pageInfo = brandService.queryPage(pageNum, pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "保存品牌")
    @PostMapping("/save")
    public Result save(@RequestBody Brand brand) {
        brandService.save(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改品牌")
    @PutMapping("/updateById")
    public Result updateById(@RequestBody Brand brand) {
        brandService.updateById(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除品牌")
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        brandService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "分页查询")
    @GetMapping("/findAll")
    public Result findAll(){
        List<Brand> list = brandService.findAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
