package com.atguigu.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.atguigu.spzx.product.mapper.ProductDetailMapper;
import com.atguigu.spzx.product.mapper.ProductMapper;
import com.atguigu.spzx.product.mapper.ProductSkuMapper;
import com.atguigu.spzx.product.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaozhen
 * @date 2023/10/30
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    ProductDetailMapper productDetailMapper;

    @Override
    public List<ProductSku> findSaleProduct() {
        //查询所有热销商品
        return productMapper.findSalePorduct();
    }

    @Override
    public PageInfo<ProductSku> queryPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page, limit);
        List<ProductSku> list = productMapper.queryPage(productSkuDto);
        return new PageInfo<>(list);
    }

    @Override
    public ProductItemVo item(Integer skuId) {
        // 通过skuId查询productSku
        ProductSku productSku = productSkuMapper.findProductSkuById(skuId);
        // 通过查询到的productSku获取到product_id,然后去查product
        Product product = productMapper.findProductById(productSku.getProductId());
        // 再通过product_id 查product_detail
        ProductDetails productDetails = productDetailMapper.findDetailById(productSku.getProductId());
        // 获取轮播图,通过split获取到图片地址的数组,再通过 Arrays.asList转乘list集合
        List<String> sliderUrlList = Arrays.asList(product.getSliderUrls().split(","));
        // 获取商品详细信息
        List<String> detailImageList = Arrays.asList(productDetails.getImageUrls().split(","));
        // 获取商品规格信息
        JSONArray specValue = JSON.parseArray(product.getSpecValue());
        // 商品规格对应商品skuId信息(Map<String,Object> skuSpecValueMap)
        // 通过产品id查询所有的sku_spec
        Map<String, Object> map = new HashMap<>();
        List<ProductSku> productSkuList = productSkuMapper.findSkuByParentId(productSku.getProductId());
        productSkuList.forEach(item->
            map.put(item.getSkuSpec(), item.getId())
        );
        // 返回ProductItemVo对象
        ProductItemVo productItemVo = new ProductItemVo();
        productItemVo.setProductSku(productSku);
        productItemVo.setProduct(product);
        productItemVo.setSliderUrlList(sliderUrlList);
        productItemVo.setDetailsImageUrlList(detailImageList);
        productItemVo.setSpecValueList(specValue);
        productItemVo.setSkuSpecValueMap(map);

        return productItemVo;
    }

}
