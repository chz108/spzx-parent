package com.atguigu.spzx.mapper;

import com.atguigu.spzx.model.entity.product.ProductSpec;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/22
 */
public interface ProductSpecMapper {
    List<ProductSpec> queryPage();

    void save(ProductSpec productSpec);

    void update(ProductSpec productSpec);

    void delete(Integer id);
}
