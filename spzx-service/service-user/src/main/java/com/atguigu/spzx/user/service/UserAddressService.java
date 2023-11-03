package com.atguigu.spzx.user.service;

import com.atguigu.spzx.model.entity.user.UserAddress;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/11/2
 */
public interface UserAddressService {
    List<UserAddress> findUserAddressList();

    void updateById(UserAddress userAddress);

    void save(UserAddress userAddress);

    void removeById(Long id);
}
