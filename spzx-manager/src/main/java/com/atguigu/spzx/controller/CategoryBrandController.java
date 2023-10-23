package com.atguigu.spzx.controller;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.service.CategoryBrandService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/21
 */
@Tag(name="分类品牌管理")
@RestController
@RequestMapping(value = "/admin/product/categoryBrand")
public class CategoryBrandController {
    @Autowired
    private CategoryBrandService categoryBrandService;

    @Operation(summary = "条件分页查询")
    @GetMapping("/{page}/{limit}")
    public Result queryPage(@PathVariable Integer page,
                            @PathVariable Integer limit,
                            CategoryBrandDto categoryBrandDto) {
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.queryPage(page, limit, categoryBrandDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加分类品牌信息")
    @PostMapping("/save")
    public Result saveCategoryBrand(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.saveCategoryBrand(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改分类品牌信息")
    @PutMapping("/updateById")
    public Result updateById(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.updateById(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "删除分类品牌信息")
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id) {
        categoryBrandService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/findBrandByCategoryId/{categoryId}")
    public Result findBrandByCategoryId(@PathVariable Long categoryId) {
        List<Brand> list =  categoryBrandService.findBrandByCategoryId(categoryId);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

}
