package com.atguigu.spzx.service;

import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/22
 */
public interface ProductSpecService {
    PageInfo<ProductSpec> queryPage(Integer page, Integer limit);

    void save(ProductSpec productSpec);

    void updateById(ProductSpec productSpec);

    void deleteById(Integer id);

    List<ProductSpec> findAll();
}
