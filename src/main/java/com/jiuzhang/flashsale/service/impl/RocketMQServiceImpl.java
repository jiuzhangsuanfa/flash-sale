package com.jiuzhang.flashsale.service.impl;

import javax.annotation.Resource;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

@Service
public class RocketMQServiceImpl {
    @Resource
    RocketMQTemplate rocketMQTemplate;

    /**
     * 发送消息
     *
     * @param topic
     * @param body
     * @throws Exception
     */
    public void sendMessage(String topic, String body)
            throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        Message message = new Message(topic, body.getBytes());
        rocketMQTemplate.getProducer().send(message, 100000);
    }

    /**
     * 发送延时消息
     *
     * @param topic
     * @param body
     * @param delayTimeLevel
     * @throws Exception
     */
    public void sendDelayMessage(String topic, String body, int delayTimeLevel)
            throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        Message message = new Message(topic, body.getBytes());
        message.setDelayTimeLevel(delayTimeLevel);
        rocketMQTemplate.getProducer().send(message);
    }

}
