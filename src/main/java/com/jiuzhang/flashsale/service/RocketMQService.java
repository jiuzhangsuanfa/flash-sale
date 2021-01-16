package com.jiuzhang.flashsale.service;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

public interface RocketMQService {

    public void sendMessage(String topic, String body)
            throws MQClientException, RemotingException, MQBrokerException, InterruptedException;

    public void sendDelayMessage(String topic, String body, int delayTimeLevel)
            throws MQClientException, RemotingException, MQBrokerException, InterruptedException;

}
