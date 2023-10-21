package com.atguigu.spzx.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/20
 */
public interface CategoryMapper {
    List<Category> findCategoryById(Long parentId);

    Integer countCategory(Long id);

    List<Category> findAll();

    void save(List<CategoryExcelVo> categoryList);
}
