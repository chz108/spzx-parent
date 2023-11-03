package com.atguigu.spzx.cart.controller;

import com.atguigu.spzx.cart.service.CartService;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/11/1
 */
@Tag(name="购物车接口管理")
@RestController
@RequestMapping("/api/order/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Operation(summary = "添加购物车")
    @GetMapping("/auth/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable("skuId") Long skuId, @PathVariable("skuNum") Integer skuNum) {
        cartService.addToCart(skuId, skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查看购物车列表")
    @GetMapping("/auth/cartList")
    public Result cartList() {
        List<CartInfo> cartInfoList = cartService.cartList();
        return Result.build(cartInfoList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除购物车中的一个商品")
    @GetMapping("/auth/deleteCart/{skuId}")
    public Result deleteCart(@PathVariable Long skuId) {
        cartService.deleteCart(skuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "更新购物车商品选中状态")
    @GetMapping("/auth/checkCart/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable Long skuId, @PathVariable Integer isChecked) {
        cartService.checkCart(skuId, isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }



    @Operation(summary = "更新购物车商品全部选中状态")
    @GetMapping("/auth/allCheckCart/{isChecked}")
    public Result allCheckCart(@PathVariable Integer isChecked) {
        cartService.allCheckCart(isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "清空购物车")
    @GetMapping("/auth/clearCart")
    public Result clearCart() {
        cartService.clearCart();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取选中状态的购物车列表")
    @GetMapping("/auth/getCheckCart")
    public List<CartInfo> getCheckCart() {
        List<CartInfo> cartInfoList = cartService.getCheckCart();
        return cartInfoList;
    }
}
