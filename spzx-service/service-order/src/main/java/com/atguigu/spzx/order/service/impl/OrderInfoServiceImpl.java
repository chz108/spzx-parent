package com.atguigu.spzx.order.service.impl;

import com.atguigu.spzx.feign.cart.CartFeignClient;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.atguigu.spzx.order.mapper.OrderInfoMapper;
import com.atguigu.spzx.order.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/11/2
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private CartFeignClient cartFeignClient;

    @Override
    public TradeVo trade() {
        // 1. 通过远程调用获取totalAmount(结算总金额)
        List<CartInfo> cartInfoList = cartFeignClient.getCheckCart();
        // 2. 确保没有精度损失问题(使用BigDecimal)
        BigDecimal totalAmount = new BigDecimal(0);
        // add 加 multiply乘
        /*
      cartInfoList.forEach(cartInfo ->
                        *//*System.out.println("cartInfo = " + cartInfo)*//*
                totalAmount.add(cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum()))));*/
        // 曾强for
        for (CartInfo cartInfo: cartInfoList) {
            totalAmount = totalAmount.add(cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum())));
        }
        // 3. 将List<cartInfo> 变成 List<OrderItem>
        List<OrderItem> orderItemList = new ArrayList<>();
        // 遍历赋值
        cartInfoList.forEach(cartInfo -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItemList.add(orderItem);
        });
        // 封装
        TradeVo tradeVo = new TradeVo();
        tradeVo.setTotalAmount(totalAmount);
        tradeVo.setOrderItemList(orderItemList);
        return tradeVo;
    }
}
