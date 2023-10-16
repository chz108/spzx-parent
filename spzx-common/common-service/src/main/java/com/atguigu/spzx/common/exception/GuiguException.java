package com.atguigu.spzx.common.exception;

import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaozhen
 * @date 2023/10/13
 */
@Data
public class GuiguException extends RuntimeException {

    private Integer code;
    private String message;

    private ResultCodeEnum resultCodeEnum;

    public GuiguException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public GuiguException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
}
