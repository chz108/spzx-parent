package com.atguigu.spzx.common.anotation;

import com.atguigu.spzx.common.config.UserWebMvcConfiguration;
import com.atguigu.spzx.common.interceptor.UserLoginAuthInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xiaozhen
 * @date 2023/10/31
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({UserWebMvcConfiguration.class, UserLoginAuthInterceptor.class})
public @interface EnableUserLoginAuthInterceptor {
}
