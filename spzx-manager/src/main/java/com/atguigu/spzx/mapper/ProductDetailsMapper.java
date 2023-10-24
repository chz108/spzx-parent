package com.atguigu.spzx.mapper;

import com.atguigu.spzx.model.entity.product.ProductDetails;

/**
 * @author xiaozhen
 * @date 2023/10/22
 */
public interface ProductDetailsMapper {
    void save(ProductDetails productDetails);

    ProductDetails findProductDetailsById(Long id);

    void update(ProductDetails productDetails);

    void delete(Long id);
}
