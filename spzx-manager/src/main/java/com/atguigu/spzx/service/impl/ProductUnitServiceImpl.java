package com.atguigu.spzx.service.impl;

import com.atguigu.spzx.mapper.ProductUnitMapper;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.atguigu.spzx.service.ProductUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/22
 */
@Service
public class ProductUnitServiceImpl implements ProductUnitService {
    @Autowired
    private ProductUnitMapper productUnitMapper;

    @Override
    public List<ProductUnit> findAll() {
        List<ProductUnit> list = productUnitMapper.findAll();
        return list;
    }
}
