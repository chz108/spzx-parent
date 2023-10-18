package com.atguigu.spzx.service;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @author xiaozhen
 * @date 2023/10/16
 */
public interface SysRoleService {
    PageInfo<SysRole> queryPage(SysRoleDto sysRoleDto, Integer current, Integer limit);

    void saveRole(SysRole sysRole);

    void updateRole(SysRole sysRole);

    void deleteRole(Integer id);

    Map<String, Object> findRoles(Integer userId);
}
