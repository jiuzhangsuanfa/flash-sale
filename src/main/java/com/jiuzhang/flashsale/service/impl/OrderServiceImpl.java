package com.jiuzhang.flashsale.service.impl;

import java.time.LocalDateTime;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiuzhang.flashsale.entity.Activity;
import com.jiuzhang.flashsale.entity.Order;
import com.jiuzhang.flashsale.exception.OrderCreateException;
import com.jiuzhang.flashsale.exception.OrderInvalidException;
import com.jiuzhang.flashsale.exception.OrderNotExistException;
import com.jiuzhang.flashsale.exception.OrderPayCheckException;
import com.jiuzhang.flashsale.exception.OrderPayException;
import com.jiuzhang.flashsale.mapper.ActivityMapper;
import com.jiuzhang.flashsale.mapper.OrderMapper;
import com.jiuzhang.flashsale.service.OrderService;
import com.jiuzhang.flashsale.service.RocketMQService;
import com.jiuzhang.flashsale.util.SnowFlake;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jiuzhang
 * @since 2021-01-15
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    ActivityMapper activityMapper;

    @Resource
    private RocketMQService rocketMQService;

    /**
     * datacenterId 数据中心
     *
     * machineId 机器标识，在分布式环境中可以从机器配置上读取，单机开发环境中可以写成常量
     */
    private final SnowFlake snowFlake = new SnowFlake(1, 1);

    @Override
    public Order createOrder(long activityId, int userId) throws OrderCreateException, OrderPayCheckException {
        Activity activity = activityMapper.selectById(activityId);
        // 1. 创建订单
        Order order = new Order();
        order.setId(snowFlake.nextId() + "");
        order.setActivityId(activityId);
        order.setUserId((long) userId);
        order.setOrderAmount(activity.getSeckillPrice());
        // 2. 发送创建订单消息
        try {
            rocketMQService.sendMessage("seckill_order", JSON.toJSONString(order));
        } catch (Exception e) {
            throw new OrderCreateException(order.getId());
        }
        // 3. 发送订单付款状态校验消息
        // 开源 RocketMQ 支持延迟消息，但是不支持秒级精度。默认支持 18 个 level 的延迟消息
        // 通过 broker 端的 messageDelayLevel 配置项确定的
        // messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        try {
            rocketMQService.sendDelayMessage("pay_check", JSON.toJSONString(order), 3);
        } catch (Exception e) {
            throw new OrderPayCheckException(order.getId());
        }
        return order;
    }

    /**
     * 支付订单处理
     *
     * @param orderId 订单 ID
     * @throws OrderPayException
     */
    @Override
    public Order payOrderProcess(String orderId)
            throws OrderNotExistException, OrderInvalidException, OrderPayException {
        log.info("完成支付订单，订单号：" + orderId);
        Order order = baseMapper.selectById(orderId);
        // 1. 判断订单是否存在
        // 2. 判断订单状态是否为未支付状态
        if (order == null) {
            log.error("订单号对应订单不存在：" + orderId);
            throw new OrderNotExistException(orderId);
        }
        if (order.getOrderStatus() != 1) {
            log.error("订单状态无效：" + orderId);
            throw new OrderInvalidException(orderId);
        }
        // 3. 订单支付完成
        order.setPayTime(LocalDateTime.now());
        // 订单状态
        // 0 没有可用库存，无效订单
        // 1 已创建等待付款
        // 2 支付完成
        order.setOrderStatus(2);
        baseMapper.updateById(order);
        // 3. 发送订单付款成功消息
        try {
            rocketMQService.sendMessage("pay_done", JSON.toJSONString(order));
        } catch (Exception e) {
            throw new OrderPayException(orderId);
        }
        return order;
    }
}
