package com.jiuzhang.flashsale.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MQException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String topic;
    private final String message;

    public MQException(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }

}
