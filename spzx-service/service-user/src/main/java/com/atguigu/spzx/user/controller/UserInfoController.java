package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;
import com.atguigu.spzx.user.service.UserInfoService;
import com.atguigu.spzx.utils.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaozhen
 * @date 2023/10/31
 */
@Tag(name = "用户接口管理")
@RestController
@RequestMapping("/api/user/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto) {
        userInfoService.register(userRegisterDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 同时更新ip地址
     * @param userLoginDto
     * @param request
     * @return
     */
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request) {
        String token = userInfoService.login(userLoginDto);
        return Result.build(token, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/auth/getCurrentUserInfo")
    public Result getCurrentUserInfo() {
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return Result.build(userInfoVo, ResultCodeEnum.SUCCESS);
    }

   /* public Result getCurrentUserInfo(@RequestHeader String token){
        UserInfoVo userInfoVo = userInfoService.getCurrentUserInfo(token);
        return Result.build(userInfoVo, ResultCodeEnum.SUCCESS);
    }*/


    @Operation(summary = "当前用户是否收藏商品")
    @GetMapping("/isCollect/{skuId}")
    public Result isCollect(@PathVariable Long skuId) {
        Boolean result = userInfoService.isCollect(skuId);
        return Result.build(result, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "当前用户收藏商品")
    @GetMapping("/auth/collect/{skuId}")
    public Result collect(@PathVariable Long skuId) {
        Boolean result = userInfoService.collect(skuId);
        return Result.build(result, ResultCodeEnum.SUCCESS);
    }


   /* @Operation(summary = "当前用户收藏商品")
    @GetMapping("/auth/findUserCollectPage/{page}/{limit}")
    public Result findUserCollectPage(@PathVariable Long page, @PathVariable Long limit) {
        PageInfo<UserCollect> pageInfo = userInfoService.findUserCollectPage(page,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }*/
}
