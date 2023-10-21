package com.atguigu.spzx.service;

import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;

/**
 * @author xiaozhen
 * @date 2023/10/20
 */
public interface BrandService {
    PageInfo queryPage(Integer pageNum, Integer pageSize);

    void save(Brand brand);

    void updateById(Brand brand);

    void deleteById(Integer id);
}
