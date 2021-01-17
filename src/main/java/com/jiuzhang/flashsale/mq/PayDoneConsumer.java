package com.jiuzhang.flashsale.mq;

import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.jiuzhang.flashsale.entity.OrderEntity;
import com.jiuzhang.flashsale.service.ActivityService;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * 支付完成消息处理 扣减库存
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "pay_done", consumerGroup = "pay_done_group")
public class PayDoneConsumer implements RocketMQListener<MessageExt> {

    @Resource
    private ActivityService activityService;

    @Override
    @Transactional
    public void onMessage(MessageExt messageExt) {
        // 1.解析创建订单请求消息
        String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        log.info("接收到创建订单请求：" + message);
        OrderEntity order = JSON.parseObject(message, OrderEntity.class);
        // 2.扣减库存
        activityService.deductStock(order.getActivityId());
    }
}
