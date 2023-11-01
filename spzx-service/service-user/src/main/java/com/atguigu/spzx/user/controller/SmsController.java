package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaozhen
 * @date 2023/10/31
 */
@Tag(name="短信接口管理")
@RestController
@RequestMapping("/api/user/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;


    @Operation(summary = "获取短信验证码")
    @GetMapping("sendCode/{phone}")
    public Result sendCode(@PathVariable String phone) {
        smsService.sendCode(phone);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
