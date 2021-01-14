package com.jiuzhang.seckill.mq;

import com.alibaba.fastjson.JSON;
import com.jiuzhang.seckill.entity.Order;
import com.jiuzhang.seckill.service.IActivityService;
import com.jiuzhang.seckill.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * 支付完成消息处理
 * 扣减库存
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "pay_done", consumerGroup = "pay_done_group")
public class PayDoneConsumer implements RocketMQListener<MessageExt> {

    @Resource
    private IActivityService activityService;

    @Override
    @Transactional
    public void onMessage(MessageExt messageExt) {
        //1.解析创建订单请求消息
        String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        log.info("接收到创建订单请求：" + message);
        Order order = JSON.parseObject(message, Order.class);
        //2.扣减库存
        activityService.deductStock(order.getActivityId());
    }
}
