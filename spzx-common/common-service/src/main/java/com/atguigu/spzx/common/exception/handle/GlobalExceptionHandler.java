package com.atguigu.spzx.common.exception.handle;

import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xiaozhen
 * @date 2023/10/13
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GuiguException.class)
    public Result guiguExceptionHandler(GuiguException guiguException) {
        return Result.build(null, guiguException.getResultCodeEnum());
    }

    @ExceptionHandler(Exception.class)
    public Result myExceptionHandler(Exception e) {
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }
}
