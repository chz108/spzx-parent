package com.atguigu.spzx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.mapper.SysUserMapper;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.service.SysUserService;
import com.atguigu.spzx.utils.AuthContextUtil;
import com.atguigu.spzx.utils.MenuHelper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaozhen
 * @date 2023/10/13
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public LoginVo login(LoginDto loginDto) {
        // 用户登录先验证验证码是否正确,然后才对用户名密码进行校验
        // 前端传递过来的验证码,把这个验证码与redis中存储的验证码进行比较,比较正确才登录
        String inputCaptcha = loginDto.getCaptcha();
        // 通过codeKey获取存储在redis中的验证码,取出redis中的验证码与前端传递过来的验证码进行比较
        String codeKey = loginDto.getCodeKey();
        // 从redis中获取验证码
        String redisCode = redisTemplate.opsForValue().get(codeKey);
        // 如果通过这个key取不到验证码,那么就抛出自定义异常,自定义异常在全局异常处理器中会进行处理
        if (StrUtil.isEmpty(redisCode)){
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        // 如果验证码输入不正确,那么就抛出自定义异常,自定义异常在全局异常处理器中会进行处理
        if (!inputCaptcha.equalsIgnoreCase(redisCode)) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        // 如果验证码输入正确那就删除掉存在redis中的验证码
        redisTemplate.delete(codeKey);
        // 通过用户名查询用户信息
        SysUser loginUser = sysUserMapper.findUserByUserName(loginDto.getUserName());
        // 如果用户不存在
        if (loginUser == null) {
            // 抛异常
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 获取用户登录的密码
        String dtoUserPwd = loginDto.getPassword();
        // 对用户登录的密码进行md5加密,并且与数据库中查询到的密码进行比对
        if (!DigestUtils.md5DigestAsHex(dtoUserPwd.getBytes()).equals(loginUser.getPassword())) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 登录成功,生成token,这里的token使用的是uuid
        String token = UUID.randomUUID().toString().replace("-", "");
        // 将token放到redis中并设置过期时间
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        // 将token设置为key,将loginUser对象转成Json数据放到value里面,过期时间为30天
        operations.set("user:login:" + token, JSON.toJSONString(loginUser), 30, TimeUnit.DAYS);

        // 将成功的数据封装到LoginVo中返回
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");
        return loginVo;
    }

    /**
     * 获取用户详情
     *
     * @return
     */
    @Override
    public SysUser getUserInfo(String token) {
        //根据token从redis中获取用户信息
        String userInfo = redisTemplate.opsForValue().get("user:login:" + token);
        SysUser sysUser = JSON.parseObject(userInfo, SysUser.class);
        return sysUser;
    }

    @Override
    public void logout(String token) {
        // 根据token从redis中删除用户信息
        redisTemplate.delete("user:login:"+token);
    }

    @Override
    public PageInfo<SysUser> queryPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> list = sysUserMapper.queryPage(sysUserDto);
        return new PageInfo<>(list);
    }

    @Override
    public void saveSysUser(SysUser sysUser) {
        SysUser loginUser = sysUserMapper.findUserByUserName(sysUser.getUserName());
        // 如果用户名已经存在就返回用户存在错误
        if (loginUser != null) {
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        // 如果用户不存在就注册新用户,密码加密
        // 设置状态正常
        sysUser.setStatus(0);
        sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        sysUserMapper.save(sysUser);
    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }

    @Override
    public void deleteSysUser(Integer id) {
        sysUserMapper.delete(id);
    }

    @Override
    public List<SysMenuVo> getMenu() {
        // 1. 通过ThreadLocal获取登录的用户信息
        SysUser sysUser = AuthContextUtil.get();
        Long userId = sysUser.getId();
        // 2. 通过userId查询这个用户的菜单列表
        List<SysMenu> sysMenus = sysUserMapper.findMenuByUserId(userId);
        List<SysMenu> buildTree = MenuHelper.buildTree(sysMenus);
        // 现在得到的是SysMenu对象,需要返回的数据是SysMenuVo对象,所以需要将SysMenu对象转成SysMenuVo对象
        List<SysMenuVo> list = buildSysMenuVo(buildTree);
        return list;
    }

    private static List<SysMenuVo> buildSysMenuVo(List<SysMenu> buildTree) {
        ArrayList<SysMenuVo> list = new ArrayList<>();
        for (SysMenu sysMenu : buildTree) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            // 判断是否为空,不为放入
            if (!CollectionUtils.isEmpty(children)) {
                // 递归层级遍历
                sysMenuVo.setChildren(buildSysMenuVo(children));
            }
            // 将对象放入集合中
            list.add(sysMenuVo);
        }
        return list;
    }
}
