package com.atguigu.spzx.product.service.impl;

import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.product.mapper.ProductMapper;
import com.atguigu.spzx.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/30
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<ProductSku> findSaleProduct() {
        //查询所有热销商品
        return productMapper.findSalePorduct();
    }
}
