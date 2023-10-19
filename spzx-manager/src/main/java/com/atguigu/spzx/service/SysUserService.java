package com.atguigu.spzx.service;

import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/13
 */
public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);

    PageInfo<SysUser> queryPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteSysUser(Integer id);

    List<SysMenuVo> getMenu();
}
