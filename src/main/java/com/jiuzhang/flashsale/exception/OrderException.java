package com.jiuzhang.flashsale.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class OrderException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String id;

    public OrderException(String id) {
        this.id = id;
    }

}
