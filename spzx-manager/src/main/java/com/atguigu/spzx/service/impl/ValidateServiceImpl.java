package com.atguigu.spzx.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import com.atguigu.spzx.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaozhen
 * @date 2023/10/15
 */
@Service
public class ValidateServiceImpl implements ValidateService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 生成 图片验证码,并将图片验证码存储到redis中
     * @return
     */
    @Override
    public ValidateCodeVo createValidateCode() {
        // 参数说明: 宽, 高 , 生成的验证码是几位数, 干扰线条数
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 10);
        // 生成的验证码
        String createCode = captcha.getCode();
        // 生成图片的base64位编码
        String createBase64 = captcha.getImageBase64();

        // 生成一个uuid用来当作验证码在redis中的key
        String redis_uuid = UUID.randomUUID().toString().replace("-", "");
        // 将验证码放入到redis中
        redisTemplate.opsForValue().set(redis_uuid, createCode, 5, TimeUnit.MINUTES);

        // 给前端放回验证码信息
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        // 校验码的时候通过这个key可以获取到redis中存储的验证码然后和前端输入的验证码进行比较
        validateCodeVo.setCodeKey(redis_uuid);
        // 前端获取到这个base64,将其生成为图片然后展示图片
        validateCodeVo.setCodeValue("data:image/png;base64,"+createBase64);
        return validateCodeVo;
    }
}
