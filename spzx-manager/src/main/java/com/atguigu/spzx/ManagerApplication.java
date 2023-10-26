package com.atguigu.spzx;

import com.atguigu.spzx.common.log.aspect.EnableLogAspect;
import com.atguigu.spzx.config.MinioProperties;
import com.atguigu.spzx.config.UserAuthProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author xiaozhen
 * @date 2023/10/13
 */
@SpringBootApplication
@MapperScan("com.atguigu.spzx.mapper")
@EnableScheduling
@EnableLogAspect
@EnableConfigurationProperties({UserAuthProperties.class, MinioProperties.class})
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
