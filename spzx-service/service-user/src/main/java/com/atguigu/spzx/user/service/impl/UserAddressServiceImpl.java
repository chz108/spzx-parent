package com.atguigu.spzx.user.service.impl;

import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.user.mapper.UserAddressMapper;
import com.atguigu.spzx.user.service.UserAddressService;
import com.atguigu.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/11/2
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;

    /**
     * 获取用户地址列表
     * @return
     */
    @Override
    public List<UserAddress> findUserAddressList() {
        return userAddressMapper.findAddressByUserId(AuthContextUtil.getUserInfo().getId());
    }

    /**
     * 修改地址
     * @param userAddress
     */
    @Override
    public void updateById(UserAddress userAddress) {
        userAddressMapper.update(userAddress);
    }

    /**
     * 添加地址
     * @param userAddress
     */
    @Override
    public void save(UserAddress userAddress) {
        userAddressMapper.save(userAddress);
    }

    /**
     * 删除地址
     * @param id
     */
    @Override
    public void removeById(Long id) {
        userAddressMapper.remove(id);
    }
}
