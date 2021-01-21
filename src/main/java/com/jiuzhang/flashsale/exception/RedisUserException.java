package com.jiuzhang.flashsale.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class RedisUserException extends RedisException {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long activityId;

    public RedisUserException(Long activityId, Long userId, String message) {
        this.activityId = activityId;
        this.userId = userId;
        this.setMessage(message + "\n" + this.toString());
    }

}
