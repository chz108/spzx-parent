package com.atguigu.spzx.controller;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.service.SysRoleMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.simpleframework.xml.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author xiaozhen
 * @date 2023/10/18
 */
@Tag(name="角色菜单接口")
@RestController
@RequestMapping("admin/system/sysRoleMenu")
public class SysRoleMenuController {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Operation(summary = "所有菜单,以及角色对应可以操作的菜单回显")
    @GetMapping("/findSysRoleMenuByRoleId/{roleId}")
    public Result findMenu(@PathVariable("roleId") Integer roleId) {
        Map<String, Object> map = sysRoleMenuService.findMenu(roleId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "为角色分配菜单")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuDto assginMenuDto) {
        sysRoleMenuService.doAssign(assginMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
