package com.atguigu.spzx.mapper;

import com.atguigu.spzx.model.entity.product.Brand;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/20
 */
public interface BrandMapper {
    List<Brand> findAll();

    Brand findBrandByName(Brand brand);

    void save(Brand brand);

    void update(Brand brand);

    void delete(Integer id);
}
