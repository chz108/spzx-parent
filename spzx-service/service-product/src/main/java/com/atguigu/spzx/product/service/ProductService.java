package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.entity.product.ProductSku;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/30
 */
public interface ProductService {
    List<ProductSku> findSaleProduct();
}
