package com.atguigu.spzx.product.service.impl;

import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.product.mapper.BrandMapper;
import com.atguigu.spzx.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/31
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }
}
