package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/30
 */
public interface ProductService {
    List<ProductSku> findSaleProduct();

    PageInfo<ProductSku> queryPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    ProductItemVo item(Integer skuId);

    ProductSku getBySkuId(Integer skuId);
}
