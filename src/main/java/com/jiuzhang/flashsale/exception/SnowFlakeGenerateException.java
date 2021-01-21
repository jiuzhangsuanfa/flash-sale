package com.jiuzhang.flashsale.exception;

public class SnowFlakeGenerateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SnowFlakeGenerateException(String message) {
        super(message);
    }

}
