package com.atguigu.spzx;

import com.atguigu.spzx.config.UserAuthProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author xiaozhen
 * @date 2023/10/13
 */
@SpringBootApplication
@MapperScan("com.atguigu.spzx.mapper")
@EnableConfigurationProperties(UserAuthProperties.class)
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}