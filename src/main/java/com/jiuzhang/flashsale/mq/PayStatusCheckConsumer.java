package com.jiuzhang.flashsale.mq;

import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.jiuzhang.flashsale.entity.OrderEntity;
import com.jiuzhang.flashsale.enums.OrderStatus;
import com.jiuzhang.flashsale.exception.RedisStockException;
import com.jiuzhang.flashsale.exception.RedisUserException;
import com.jiuzhang.flashsale.service.ActivityService;
import com.jiuzhang.flashsale.service.OrderService;
import com.jiuzhang.flashsale.service.impl.RedisServiceImpl;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RocketMQMessageListener(topic = "pay_check", consumerGroup = "pay_check_group")
public class PayStatusCheckConsumer implements RocketMQListener<MessageExt> {

    @Resource
    private OrderService orderService;

    @Resource
    private ActivityService activityService;

    @Resource
    private RedisServiceImpl redisService;

    @Override
    @Transactional
    public void onMessage(MessageExt messageExt) {
        String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        log.info("接收到订单支付状态校验消息:" + message);
        OrderEntity order = JSON.parseObject(message, OrderEntity.class);
        // 1.查询订单
        OrderEntity orderInfo = orderService.getById(order.getId());
        // 2.判读订单是否完成支付
        try {
            if (orderInfo.getOrderStatus() != OrderStatus.PAID) {
                // 3.未完成支付关闭订单
                log.info("未完成支付关闭订单,订单号：" + orderInfo.getId());
                orderInfo.setOrderStatus(OrderStatus.CLOSED);
                orderService.updateById(orderInfo);
                // 4.恢复数据库库存
                activityService.revertStock(order.getActivityId());
                // 恢复 redis 库存
                redisService.revertStock(order.getActivityId());
                // 5.将用户从已购名单中移除
                redisService.removeRestrictedUser(order.getActivityId(), order.getUserId());
            }
        } catch (RedisStockException exception) {
            log.error("redis stock revert failed, order id: " + order.getId());
        } catch (RedisUserException exception) {
            log.error("redis restricted user removed failed, order id: " + order.getId());
        } catch (Exception exception) {
            log.error("stock revert failed, order id: " + order.getId());
        }
    }

}
