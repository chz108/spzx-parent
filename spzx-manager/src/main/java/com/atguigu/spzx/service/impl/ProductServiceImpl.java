package com.atguigu.spzx.service.impl;

import com.atguigu.spzx.mapper.ProductDetailsMapper;
import com.atguigu.spzx.mapper.ProductMapper;
import com.atguigu.spzx.mapper.ProductSkuMapper;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/22
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Override
    public PageInfo<Product> queryPage(Integer page, Integer limit, ProductDto productDto) {
        PageHelper.startPage(page, limit);
        List<Product> list = productMapper.queryPage(productDto);
        return new PageInfo<>(list);
    }

    @Override
    public void save(Product product) {
        // 需要将状态设置位初始值
        product.setStatus(0);
        product.setAuditStatus(0);
        // 将商品存入product表中,因为他的主键需要被其它表使用
        productMapper.save(product);
        // 从product中获取到用户填写的商品sku规格
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (ProductSku productSku : productSkuList) {
            // product_sku表与product表是用product的id和product_sku表的product_id关联起来的
            // 所以需要将product的id先获取到(使用主键自增直接获取到),再存入到product_sku表中
            productSku.setProductId(product.getId());
            // 设置商品编号,使用唯一值
            productSku.setSkuCode(product.getId()+"_"+(productSku.getId()-1));
            // skuName = 商品名称+规格
            productSku.setSkuName(product.getName() + productSku.getSkuSpec());
            // 设置销量
            productSku.setSaleNum(0);
            productSku.setStatus(0);
            productSkuMapper.save(productSku);                    // 保存数据
        }
        // 向商品详情表中插入商品的图片地址,将商品的id放入到商品详情中
        ProductDetails pd = new ProductDetails();
        pd.setImageUrls(product.getDetailsImageUrls());
        pd.setProductId(product.getId());
        productDetailsMapper.save(pd);
    }
}
