package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.Brand;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/31
 */
public interface BrandMapper {
    List<Brand> findAll();
}
