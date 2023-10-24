package com.atguigu.spzx.mapper;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/22
 */
public interface ProductSkuMapper {
    List<ProductSku> findProductSkuById(Long productId);

    void save(ProductSku productSku);

    void update(ProductSku productSku);

    void delete(Long id);
}
