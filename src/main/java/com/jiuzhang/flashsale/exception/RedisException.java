package com.jiuzhang.flashsale.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RedisException extends Exception {

    private static final long serialVersionUID = 1L;

    private String message;

    public RedisException(String message) {
        this.message = message;
    }

}
