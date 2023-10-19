package com.atguigu.spzx.controller;

import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.entity.system.SysRoleUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.service.SysRoleUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaozhen
 * @date 2023/10/17
 */
@Tag(name = "角色用户接口")
@RestController
@RequestMapping("/admin/system/sysRoleUser")
public class SysRoleUserController {
    @Autowired
    private SysRoleUserService sysRoleUserService;

    @Operation(summary = "给用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assignRoleDto) {
        sysRoleUserService.doAssign(assignRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
