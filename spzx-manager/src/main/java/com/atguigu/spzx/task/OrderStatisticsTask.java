package com.atguigu.spzx.task;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.mapper.OrderInfoMapper;
import com.atguigu.spzx.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author xiaozhen
 * @date 2023/10/23
 */
@Component
public class OrderStatisticsTask {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    @Scheduled(cron = "0 0 11 * * ?")
    public void orderTask() {
        String date = DateUtil.offsetDay(new Date(), -1).toString("yyyy-MM-dd");
        OrderStatistics orderStatistics = orderInfoMapper.selectOrderStatistics(date);
        if (orderStatistics != null){ orderStatisticsMapper.save(orderStatistics);}
        System.out.println(date);
    }
}
