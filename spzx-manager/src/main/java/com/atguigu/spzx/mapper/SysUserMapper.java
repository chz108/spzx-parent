package com.atguigu.spzx.mapper;

import com.atguigu.spzx.model.entity.system.SysUser;

/**
 * @author xiaozhen
 * @date 2023/10/13
 */
public interface SysUserMapper {
    SysUser findUserByUserName(String userName);
}
