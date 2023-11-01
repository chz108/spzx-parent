package com.atguigu.spzx.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;
import com.atguigu.spzx.user.mapper.UserInfoMapper;
import com.atguigu.spzx.user.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaozhen
 * @date 2023/10/31
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void register(UserRegisterDto userRegisterDto) {
        // 判断前端传递过来的数据是否为空
        if (userRegisterDto == null) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        // 首先需要对验证码进行判断
        String code = userRegisterDto.getCode();
        if (code == null) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        // 通过用户名获取到存在redis中的验证码,与redis中的验证码进行比较
        String redisCode = redisTemplate.opsForValue().get(userRegisterDto.getUsername());
        if (!code.equals(redisCode)) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        // 进行注册
        // 1. 判断用户是否存在,如果存在返回用户名已存在
        UserInfo userInfo = userInfoMapper.findUserByUserName(userRegisterDto.getUsername());
        if (userInfo != null) {
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        // 2. 如果不存在允许就将用户注册
        UserInfo user = new UserInfo();
        user.setUsername(userRegisterDto.getUsername());
        // 密码加密存入
        user.setPassword(DigestUtils.md5DigestAsHex(userRegisterDto.getPassword().getBytes()));
        user.setNickName(userRegisterDto.getNickName());
        user.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        user.setPhone(userRegisterDto.getUsername());
        user.setSex(0);
        user.setStatus(1);
        userInfoMapper.register(user);
        // 最后删除redis中的验证
        redisTemplate.delete(userRegisterDto.getUsername());
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        // 1. 判断用户名是否为空
        if (userLoginDto.getUsername() == null) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        // 2. 通过用户名查询用户信息
        UserInfo user = userInfoMapper.findUserByUserName(userLoginDto.getUsername());
        if (user == null) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 3. 对获取到的密码加密与数据库中的密码作比较
        String password = userLoginDto.getPassword();
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!md5Password.equals(user.getPassword())) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 4. 判断用户是否已停用
        if(user.getStatus() == 0) {
            throw new GuiguException(ResultCodeEnum.ACCOUNT_STOP);
        }
        // 5. 生成随机token
        String token = UUID.randomUUID().toString().replace("-", "");
        // 6. 将用户信息存入到redis中
        redisTemplate.opsForValue().set(token, JSON.toJSONString(user), 30, TimeUnit.DAYS);
        return token;
    }

    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
        // 通过redis获取用户的基本信息
        String userInfoStr = redisTemplate.opsForValue().get(token);
        UserInfo userInfo = JSON.parseObject(userInfoStr, UserInfo.class);
        if (userInfo == null) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return userInfoVo;
    }

}
