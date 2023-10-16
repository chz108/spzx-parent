package com.atguigu.spzx.controller;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.service.SysRoleService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.aot.ApplicationContextAotGenerator;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaozhen
 * @date 2023/10/16
 */
@Tag(name = "角色接口")
@RestController
@RequestMapping("/admin/system/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @Operation(summary = "条件分页查询")
    @PostMapping("/queryPage/{current}/{limit}")
    public Result queryPage(@RequestBody(required = false) SysRoleDto sysRoleDto,
                            @PathVariable Integer current,
                            @PathVariable Integer limit) {
        PageInfo<SysRole> pageInfo = sysRoleService.queryPage(sysRoleDto, current, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "角色添加")
    @PostMapping("/saveRole")
    public Result saveRole(@RequestBody SysRole sysRole) {
        sysRoleService.saveRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 角色
     * @param sysRole
     * @return
     */
    @Operation(summary = "角色修改")
    @PutMapping("/updateRole")
    public Result updateRole(@RequestBody SysRole sysRole) {
        sysRoleService.updateRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 逻辑删除
     * @param id 角色id
     * @return
     */
    @Operation(summary = "角色删除")
    @DeleteMapping("/deleteRole/{id}")
    public Result deleteRole(@PathVariable Integer id) {
        sysRoleService.deleteRole(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
