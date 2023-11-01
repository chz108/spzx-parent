package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.ProductSku;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/31
 */
public interface ProductSkuMapper {

    /**
     * 通过skuId查询productSku
     * @param skuId
     * @return
     */
    ProductSku findProductSkuById(Integer skuId);

    List<ProductSku> findSkuByParentId(Long productId);
}
