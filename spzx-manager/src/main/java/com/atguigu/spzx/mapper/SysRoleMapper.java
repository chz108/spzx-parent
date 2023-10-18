package com.atguigu.spzx.mapper;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/16
 */
public interface SysRoleMapper {
    List<SysRole> findPageRoleList(SysRoleDto sysRoleDto);

    void save(SysRole sysRole);

    void update(SysRole sysRole);

    void delete(Integer id);

    List<SysRole> findAll();

    List<Integer> findByUserId(Integer userId);
}
