package com.atguigu.spzx.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author xiaozhen
 * @date 2023/10/30
 */
@SpringBootApplication
@MapperScan("com.atguigu.spzx.product.mapper")
@ComponentScan(basePackages = "com.atguigu.spzx")
@EnableCaching
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
}
