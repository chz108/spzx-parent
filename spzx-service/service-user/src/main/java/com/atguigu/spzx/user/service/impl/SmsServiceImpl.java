package com.atguigu.spzx.user.service.impl;

import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.service.SmsService;
import com.atguigu.spzx.utils.HttpUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaozhen
 * @date 2023/10/31
 */
@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void sendCode(String phone) {
        // 首先先判断一下redis中是否已经有了验证码
        String findCode = redisTemplate.opsForValue().get(phone);
        // 如果不为空就直接返回
        if (findCode != null) {
            return;
        }
        // 生成随机四位的验证码,使用工具类生成
        String code = RandomStringUtils.randomNumeric(4);
        // 将code放入redis中
        redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
        toSendPhone(phone, code);
    }

    private void toSendPhone(String phone, String code) {
        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "89a6dfcacc6c4e7b91d900ee5f3b2d97";
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<>();
        Map<String, String> bodys = new HashMap<>();
        bodys.put("content", "code:"+code);
        bodys.put("template_id", "CST_ptdie100");
        bodys.put("phone_number", phone);


        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}
