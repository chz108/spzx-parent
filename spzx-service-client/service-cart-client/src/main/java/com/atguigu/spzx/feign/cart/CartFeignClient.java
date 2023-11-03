package com.atguigu.spzx.feign.cart;

import com.atguigu.spzx.model.entity.h5.CartInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/11/2
 */
@FeignClient(value = "service-cart")
public interface CartFeignClient {

    @GetMapping("/api/order/cart/auth/getCheckCart")
    public List<CartInfo> getCheckCart();
}
