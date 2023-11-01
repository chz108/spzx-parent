package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductSku;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/30
 */
public interface ProductMapper {
    //查询所有热销商品
    List<ProductSku> findSalePorduct();

    List<ProductSku> queryPage(ProductSkuDto productSkuDto);

    Product findProductById(Long productId);
}
