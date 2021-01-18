package com.jiuzhang.flashsale.service.impl;

import javax.annotation.Resource;

import com.jiuzhang.flashsale.exception.MQException;
import com.jiuzhang.flashsale.service.RocketMQService;

import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

@Service
public class RocketMQServiceImpl implements RocketMQService {

    @Resource
    RocketMQTemplate rocketMQTemplate;

    @Override
    public void sendMessage(String topic, String body) throws MQException {
        try {
            Message message = new Message(topic, body.getBytes());
            rocketMQTemplate.getProducer().send(message, 100000);
        } catch (Exception exception) {
            throw new MQException(topic, exception.getMessage());
        }
    }

    @Override
    public void sendDelayMessage(String topic, String body, int delayTimeLevel) throws MQException {
        try {
            Message message = new Message(topic, body.getBytes());
            message.setDelayTimeLevel(delayTimeLevel);
            rocketMQTemplate.getProducer().send(message);
        } catch (Exception exception) {
            throw new MQException(topic, exception.getMessage());
        }
    }

}
