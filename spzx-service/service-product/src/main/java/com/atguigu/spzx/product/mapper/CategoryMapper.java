package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.Category;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/30
 */
public interface CategoryMapper {
    //查询所有一级分类
    List<Category> findOneCategory();

    List<Category> findAll();
}
