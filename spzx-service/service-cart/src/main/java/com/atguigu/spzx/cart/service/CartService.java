package com.atguigu.spzx.cart.service;

import com.atguigu.spzx.model.entity.h5.CartInfo;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/11/1
 */
public interface CartService {
    void addToCart(Long skuId, Integer skuNum);

    List<CartInfo> cartList();

    void deleteCart(Long skuId);

    void checkCart(Long skuId, Integer isChecked);

    void allCheckCart(Integer isChecked);

    void clearCart();

    List<CartInfo> getCheckCart();
}
