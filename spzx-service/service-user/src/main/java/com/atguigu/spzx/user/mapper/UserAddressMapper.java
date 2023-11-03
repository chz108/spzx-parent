package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.user.UserAddress;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/11/2
 */
public interface UserAddressMapper {
    List<UserAddress> findAddressByUserId(Long id);

    void update(UserAddress userAddress);

    void save(UserAddress userAddress);

    void remove(Long id);
}
