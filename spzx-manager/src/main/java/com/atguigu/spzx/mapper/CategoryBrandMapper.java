package com.atguigu.spzx.mapper;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/21
 */
public interface CategoryBrandMapper {
    List<CategoryBrand> queryPage(CategoryBrandDto categoryBrandDto);

    int save(CategoryBrand categoryBrand);

    void update(CategoryBrand categoryBrand);

    void delete(Long id);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
