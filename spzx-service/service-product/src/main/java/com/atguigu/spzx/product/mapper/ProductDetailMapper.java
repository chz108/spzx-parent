package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.ProductDetails;

/**
 * @author xiaozhen
 * @date 2023/10/31
 */
public interface ProductDetailMapper {

    /**
     * 通过productId获取到product详情
     * @param productId
     * @return
     */
    ProductDetails findDetailById(Long productId);
}
