package com.jiuzhang.flashsale.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class RedisStockException extends RedisException {

    private static final long serialVersionUID = 1L;

    private Long activityId;

    public RedisStockException(Long activityId, String message) {
        this.activityId = activityId;
        this.setMessage(message + "\n" + this.toString());
    }

}
