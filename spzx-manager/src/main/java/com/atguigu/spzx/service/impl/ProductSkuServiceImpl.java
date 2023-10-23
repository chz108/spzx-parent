package com.atguigu.spzx.service.impl;

import com.atguigu.spzx.mapper.ProductSkuMapper;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.service.ProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaozhen
 * @date 2023/10/22
 */
@Service
public class ProductSkuServiceImpl implements ProductSkuService {
    @Autowired
    private ProductSkuMapper productSkuMapper;

    private void save(ProductSku productSku) {
        productSkuMapper.save(productSku);
    }
}
