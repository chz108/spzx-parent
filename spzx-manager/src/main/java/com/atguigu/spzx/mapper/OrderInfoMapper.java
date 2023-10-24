package com.atguigu.spzx.mapper;

import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderStatistics;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/23
 */
public interface OrderInfoMapper {
    OrderStatistics selectOrderStatistics(String creatTime);

}
