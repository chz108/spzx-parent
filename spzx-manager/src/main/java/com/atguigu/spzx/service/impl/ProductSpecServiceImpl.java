package com.atguigu.spzx.service.impl;

import com.atguigu.spzx.mapper.ProductSpecMapper;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.atguigu.spzx.service.ProductSpecService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/22
 */
@Service
public class ProductSpecServiceImpl implements ProductSpecService {

    @Autowired
    private ProductSpecMapper productSpecMapper;

    @Override
    public PageInfo<ProductSpec> queryPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<ProductSpec> list = productSpecMapper.queryPage();
        return new PageInfo<>(list);
    }

    @Override
    public void save(ProductSpec productSpec) {
        productSpecMapper.save(productSpec);
    }

    @Override
    public void updateById(ProductSpec productSpec) {
        productSpecMapper.update(productSpec);
    }

    @Override
    public void deleteById(Integer id) {
        productSpecMapper.delete(id);
    }

    @Override
    public List<ProductSpec> findAll() {
        return productSpecMapper.queryPage();
    }

}
