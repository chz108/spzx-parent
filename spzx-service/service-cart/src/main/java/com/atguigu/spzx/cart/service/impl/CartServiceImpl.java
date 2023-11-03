package com.atguigu.spzx.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.cart.service.CartService;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/11/1
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ProductFeignClient productFeignClient;


    /**
     * 添加购物车 ,如果购物车里有更新数量,如果没有添加
     * @param skuId
     * @param skuNum
     */
    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        // 1. 获取到用户id
        Long userId = AuthContextUtil.getUserInfo().getId();
        String key = makeKey(userId);
         //2. 查询redis中是否已经存在了这个用户是否已经添加过这个商品
        Object obj = redisTemplate.opsForHash().get(key, skuId.toString());
        CartInfo cartInfo = null;
        if (obj != null) {
            // 3. 如果已经存在了这个商品那么就对这个商品的skuNum(数量+skuNum)
            cartInfo = JSON.parseObject(obj.toString(), CartInfo.class);
            // 获取再相加
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
            // 更新redis中的数据
            redisTemplate.opsForHash().put(key, skuId.toString(), JSON.toJSONString(cartInfo));
        }else{
            // 如果不存在就往redis中存一个
            cartInfo = new CartInfo();
            // 远程调用通过skuId获取productSku的数据
            ProductSku productSku = productFeignClient.getBySkuId(skuId.intValue());
            // 将productSku数据放到cartInfo中存到redis中
            // 售价
            cartInfo.setCartPrice(productSku.getSalePrice());
            // 数量
            cartInfo.setSkuNum(skuNum);
            // skuId
            cartInfo.setSkuId(skuId);
            // userId
            cartInfo.setUserId(userId);
            // 缩略图路径
            cartInfo.setImgUrl(productSku.getThumbImg());
            // skuName
            cartInfo.setSkuName(productSku.getSkuName());
            // 是否选中
            cartInfo.setIsChecked(1);
            // 创建时间
            cartInfo.setCreateTime(new Date());
            // 修改时间
            cartInfo.setUpdateTime(new Date());
            // 存入redis中
            redisTemplate.opsForHash().put(key, skuId.toString(), JSON.toJSONString(cartInfo));
        }
    }

    /**
     * 查看购物车列表
     *
     * @return
     */
    @Override
    public List<CartInfo> cartList() {
        // 1. 通过userId获取到redis中全部的数据
        String key = makeKey(AuthContextUtil.getUserInfo().getId());
        // 获取到objectList列表
        List<Object> objectList = redisTemplate.opsForHash().values(key);
        // 判断是否为空,如果不为空
        if (!CollectionUtils.isEmpty(objectList)) {
            // objectList (List<Object>)---> List<CartInfo> (使用stream流的方式)
            List<CartInfo> cartInfoList = objectList.stream().map(obj ->
                    JSON.parseObject(obj.toString(), CartInfo.class)
            ).toList();
            // 返回
            return cartInfoList;
        }
        // 如果为空就返回前端一个空集合,让前端自己处理
        return new ArrayList<>();
    }

    /**
     * 删除购物车中的商品
     * @param skuId
     */
    @Override
    public void deleteCart(Long skuId) {
        // 1. 通过userId获取到redis中全部的数据
        String key = makeKey(AuthContextUtil.getUserInfo().getId());
        // 2. 通过key 和 skuId 删除redis中的数据
        redisTemplate.opsForHash().delete(key, skuId.toString());
    }

    /**
     * 更新购物车商品选中状态
     * @param skuId
     * @param isChecked
     */
    @Override
    public void checkCart(Long skuId, Integer isChecked) {
        // 1. 通过userId获取到redis中全部的数据
        String key = makeKey(AuthContextUtil.getUserInfo().getId());
       /* // 2. 获取到redis中的购物车商品数据
        Object obj = redisTemplate.opsForHash().get(key, skuId.toString());
        if (obj != null) {
            // 3. 将obj 装成cartInfo 然后再set值
            CartInfo cartInfo = JSON.parseObject(obj.toString(), CartInfo.class);
            cartInfo.setIsChecked(isChecked);
        }*/
        // 2. 判断redis中是否有这个k商品
        Boolean result = redisTemplate.opsForHash().hasKey(key, skuId.toString());
        if (result) {
            Object obj = redisTemplate.opsForHash().get(key, skuId.toString());
            // 3. 将obj 装成cartInfo 然后再set值
            if (obj != null) {
                // 3. 将obj 装成cartInfo 然后再set值
                CartInfo cartInfo = JSON.parseObject(obj.toString(), CartInfo.class);
                cartInfo.setIsChecked(isChecked);
            }
        }
    }

    /**
     * 更新购物车商品全部选中状态
     * @param isChecked
     */
    @Override
    public void allCheckCart(Integer isChecked) {
        // 1. 通过userId获取到redis中全部的数据
        String key = makeKey(AuthContextUtil.getUserInfo().getId());
        // 2. 获取redis中全部的商品数据
        List<Object> objectList = redisTemplate.opsForHash().values(key);
        // 3. 需要将List<Object> --> List<CartInfo>
        List<CartInfo> cartInfoList = objectList.stream()
                                                .map(obj -> JSON.parseObject(obj.toString(), CartInfo.class))
                                                .toList();
        // 4. 循环取出list中的carInfo将每一个cartInfo的isChecked修改为需要的值
        cartInfoList.forEach(cartInfo -> {
            cartInfo.setIsChecked(isChecked);
            // 将 修改好的cartInfo重新放回到redis中
            redisTemplate.opsForHash().put(key, cartInfo.getSkuId().toString(),JSON.toJSONString(cartInfo));
        });
    }

    @Override
    public void clearCart() {
        // 1. 通过userId获取到redis中全部的数据
        String key = makeKey(AuthContextUtil.getUserInfo().getId());
        // 2. 清空与这个用户有关的购物车的redis数据
        redisTemplate.delete(key);
    }

    /**
     * 获取选中状态的购物车列表
     * @return
     */
    @Override
    public List<CartInfo> getCheckCart() {
        // 1. 通过userId获取到redis中全部的数据
        String key = makeKey(AuthContextUtil.getUserInfo().getId());
        // 2. 获取所有
        List<Object> objectList = redisTemplate.opsForHash().values(key);
        // 3. 转类型
        List<CartInfo> cartInfoList = objectList.stream().map(obj ->
                JSON.parseObject(obj.toString(), CartInfo.class)
        ).toList();
        // 4. 过滤
        List<CartInfo> list = cartInfoList.stream().filter(cartInfo -> cartInfo.getIsChecked() == 1).toList();
        return list;
    }

    private String makeKey(Long userId) {
        return "user:card:" + userId;
    }

}
