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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    @Transactional
    @Override
    public void save(Product product) {
        // 需要将状态设置位初始值
        product.setStatus(0);
        product.setAuditStatus(0);
        // 将商品存入product表中,因为他的主键需要被其它表使用
        productMapper.save(product);
        System.out.println(product);
        // 从product中获取到用户填写的商品sku规格
        System.out.println(product.getId());
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (ProductSku productSku : productSkuList) {
            // product_sku表与product表是用product的id和product_sku表的product_id关联起来的
            // 所以需要将product的id先获取到(使用主键自增直接获取到),再存入到product_sku表中
            productSku.setProductId(product.getId());
            // 设置商品编号,使用唯一值
            String s = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            productSku.setSkuCode(product.getId() + "_" + s);
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

    @Override
    public Product getById(Long id) {
        // 1.查询product的基本信息 通过商品id查询商品信息
        Product product = productMapper.findProductById(id);
        // 2. 查询product_sku的信息 通过商品id查询商品的sku信息
        List<ProductSku> skuList = productSkuMapper.findProductSkuById(id);
        // 3. 查询product_detail的信息 通过商品id查询商品的纤细图片地址
        ProductDetails productDetails = productDetailsMapper.findProductDetailsById(id);
        // 4. 将skuList集合中的数据set到product中
        product.setProductSkuList(skuList);
        // 5. 将图片反倒product中
        product.setDetailsImageUrls(productDetails.getImageUrls());
        // 返回
        return product;
    }

    @Transactional
    @Override
    public void updateById(Product product) {
        // 1. 修改product的基本信息
        productMapper.update(product);
        // 2. 修改product_sku的信息,从product中获取到sku的数据到表中修改
        List<ProductSku> productSkuList = product.getProductSkuList();
        productSkuList.forEach(productSku -> productSkuMapper.update(productSku));
        // 3. 修改product_detail的信息
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.update(productDetails);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        // 1. 删除product
        productMapper.delete(id);
        // 2. 删除product_sku
        productSkuMapper.delete(id);
        // 3. 删除product_detail
        productDetailsMapper.delete(id);
    }

    // 审核是否通过
    @Override
    public void updateAuditStatus(Long id, Integer auditStatus) {
        Product product = new Product();
        product.setId(id);
        product.setAuditStatus(auditStatus);
        if (auditStatus == 1) {
          product.setAuditMessage("审核通过");
        }else{
            product.setAuditMessage("驳回");
        }
        productMapper.update(product);
    }

    // 商品是否下架
    @Override
    public void updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        product.setStatus(status);
        productMapper.update(product);
    }
}
