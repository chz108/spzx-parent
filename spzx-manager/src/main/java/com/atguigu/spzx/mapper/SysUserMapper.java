package com.atguigu.spzx.mapper;

import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.entity.system.SysUser;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/13
 */
public interface SysUserMapper {
    SysUser findUserByUserName(String userName);

    List<SysUser> queryPage(SysUserDto sysUserDto);

    void save(SysUser sysUser);

    void update(SysUser sysUser);

    void delete(Integer id);

    List<SysMenu> findMenuByUserId(Long userId);
}
