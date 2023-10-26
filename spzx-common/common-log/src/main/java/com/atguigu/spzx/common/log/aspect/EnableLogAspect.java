package com.atguigu.spzx.common.log.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaozhen
 * @date 2023/10/24
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(LogAspect.class)
public @interface EnableLogAspect {
}
