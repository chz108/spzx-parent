package com.atguigu.spzx.service;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.github.pagehelper.PageInfo;

/**
 * @author xiaozhen
 * @date 2023/10/22
 */
public interface ProductService {
    PageInfo<Product> queryPage(Integer page, Integer limit, ProductDto productDto);

    void save(Product product);
}
