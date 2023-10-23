package com.atguigu.spzx.service;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/21
 */
public interface CategoryBrandService {
    PageInfo<CategoryBrand> queryPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

    void saveCategoryBrand(CategoryBrand categoryBrand);

    void updateById(CategoryBrand categoryBrand);

    void deleteById(Long id);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
