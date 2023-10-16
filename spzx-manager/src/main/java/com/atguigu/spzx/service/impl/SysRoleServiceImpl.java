package com.atguigu.spzx.service.impl;

import com.atguigu.spzx.mapper.SysRoleMapper;
import com.atguigu.spzx.mapper.SysUserMapper;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.service.SysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/16
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public PageInfo<SysRole> queryPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
        PageHelper.startPage(current, limit);
        List<SysRole> sysRoleList = sysRoleMapper.findPageRoleList(sysRoleDto);
        PageInfo<SysRole> pageInfo = new PageInfo<>(sysRoleList);
        return pageInfo;
    }

    @Override
    public void saveRole(SysRole sysRole) {
        sysRoleMapper.save(sysRole);
    }

    @Override
    public void updateRole(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
    }

    @Override
    public void deleteRole(Integer id) {
        sysRoleMapper.delete(id);
    }

}
