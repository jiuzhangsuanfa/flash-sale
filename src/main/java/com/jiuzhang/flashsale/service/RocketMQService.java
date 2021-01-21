package com.jiuzhang.flashsale.service;

import com.jiuzhang.flashsale.exception.MQException;

/**
 * Rocket MQ 服务
 *
 * @author jiuzhang
 * @since 2021-01-17
 */
public interface RocketMQService {

    /**
     * 发送消息
     *
     * @param topic 消息通道（主题）
     * @param body  消息内容
     * @throws MQException 发送消息失败的异常
     */
    public void sendMessage(String topic, String body) throws MQException;

    /**
     * 发送延迟消息
     *
     * @param topic          消息通道（主题）
     * @param body           消息内容
     * @param delayTimeLevel 延迟消息等级
     * @throws MQException 发送消息失败的异常
     */
    public void sendDelayMessage(String topic, String body, int delayTimeLevel) throws MQException;

}
