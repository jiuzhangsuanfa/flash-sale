package com.jiuzhang.flashsale.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class RedisDistributedLockException extends RedisException {

    private static final long serialVersionUID = 1L;

    private String lockKey;

    private String requestId;

    public RedisDistributedLockException(String lockKey, String requestId, String message) {
        this.lockKey = lockKey;
        this.requestId = requestId;
        this.setMessage(message + "\n" + this.toString());
    }

}
