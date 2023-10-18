package com.atguigu.spzx.controller;

import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.service.SysUserService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaozhen
 * @date 2023/10/17
 */
@Tag(name = "系统用户接口")
@RestController
@RequestMapping("/admin/system/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @Operation(summary = "用户分页查询")
    @PostMapping("/queryPage/{pageNum}/{pageSize}")
    public Result queryPage(@PathVariable Integer pageNum,
                            @PathVariable Integer pageSize,
                            @RequestBody(required = false) SysUserDto sysUserDto) {
        PageInfo<SysUser> pageInfo = sysUserService.queryPage(pageNum, pageSize, sysUserDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "用户新增")
    @PostMapping("/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser) {
        sysUserService.saveSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "用户修改")
    @PutMapping("/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser) {
        sysUserService.updateSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "用户删除")
    @DeleteMapping("/deleteSysUser/{id}")
    public Result deleteSysUser(@PathVariable Integer id) {
        sysUserService.deleteSysUser(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
