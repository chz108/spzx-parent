package com.atguigu.spzx.mapper;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/22
 */
public interface ProductMapper {
    List<Product> queryPage(ProductDto productDto);

    List<Product> findAll();

    void save(Product product);
}
