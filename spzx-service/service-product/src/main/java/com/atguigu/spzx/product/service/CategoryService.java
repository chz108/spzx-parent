package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.entity.product.Category;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/30
 */
public interface CategoryService {
    List<Category> findOneCategory();

    List<Category> findCategoryTree();
}
