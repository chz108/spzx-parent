package com.atguigu.spzx.service.impl;

import com.atguigu.spzx.mapper.SysRoleUserMapper;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.service.SysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/17
 */
@Service
public class SysRoleUserServiceImpl implements SysRoleUserService {
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Override
    public void doAssign(AssginRoleDto assignRoleDto) {
        sysRoleUserMapper.delete(assignRoleDto.getUserId());
        Long userId = assignRoleDto.getUserId();
        List<Long> roleIdList = assignRoleDto.getRoleIdList();
        for (Long roleId : roleIdList) {
            sysRoleUserMapper.saveSysRoleUser(userId, roleId);
        }

    }
}
