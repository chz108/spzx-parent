package com.atguigu.spzx.order;

import com.atguigu.spzx.common.anotation.EnableUserLoginAuthInterceptor;
import com.atguigu.spzx.common.anotation.EnableUserTokenFeignInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xiaozhen
 * @date 2023/11/2
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.atguigu.spzx"})
@MapperScan("com.atguigu.spzx.order.mapper")
@EnableUserLoginAuthInterceptor
@EnableUserTokenFeignInterceptor
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
