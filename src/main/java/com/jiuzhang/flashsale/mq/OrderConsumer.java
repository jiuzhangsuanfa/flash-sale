package com.jiuzhang.flashsale.mq;

import com.alibaba.fastjson.JSON;
import com.jiuzhang.flashsale.entity.Order;
import com.jiuzhang.flashsale.service.IActivityService;
import com.jiuzhang.flashsale.service.IOrderService;
import com.jiuzhang.flashsale.service.RedisService;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@Component
@RocketMQMessageListener(topic = "seckill_order", consumerGroup = "seckill_order_group")
public class OrderConsumer implements RocketMQListener<MessageExt> {
    @Resource
    private IOrderService orderService;
    @Resource
    private IActivityService activityService;
    @Resource
    RedisService redisService;

    @Override
    @Transactional
    public void onMessage(MessageExt messageExt) {
        // 1.解析创建订单请求消息
        String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        log.info("接收到创建订单请求：" + message);
        Order order = JSON.parseObject(message, Order.class);
        order.setCreateTime(LocalDateTime.now());
        // 2.扣减库存
        boolean lockStockResult = activityService.lockStock(order.getActivityId());
        if (lockStockResult) {
            // 订单状态 0:没有可用库存，无效订单 1:已创建等待付款
            order.setOrderStatus(1);
            // 将用户加入到限购用户中
            redisService.addLimitMember(order.getActivityId(), order.getUserId());
        } else {
            order.setOrderStatus(0);
        }
        // 3.插入订单
        orderService.save(order);
    }
}
