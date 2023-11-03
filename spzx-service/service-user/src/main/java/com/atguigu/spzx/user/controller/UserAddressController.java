package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/11/2
 */
@Tag(name = "用户地址管理")
@RestController
@RequestMapping("/api/user/userAddress")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    @Operation(summary = "获取用户地址列表")
    @GetMapping("/auth/findUserAddressList")
    public Result findUserAddressList() {
        List<UserAddress> userAddressList = userAddressService.findUserAddressList();
        return Result.build(userAddressList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改用户地址")
    @PutMapping("/auth/updateById")
    public Result updateById(@RequestBody UserAddress userAddress) {
        userAddressService.updateById(userAddress);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "新增用户地址列表")
    @PostMapping("/auth/save")
    public Result save(@RequestBody UserAddress userAddress) {
        userAddressService.save(userAddress);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "删除用户地址")
    @PostMapping("/removeById/{id}")
    public Result removeById(@PathVariable Long id) {
        userAddressService.removeById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
