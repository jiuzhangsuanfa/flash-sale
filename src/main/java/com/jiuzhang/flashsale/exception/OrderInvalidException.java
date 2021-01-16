package com.jiuzhang.flashsale.exception;

public class OrderInvalidException extends OrderException {

    private static final long serialVersionUID = 1L;

    public OrderInvalidException(String id) {
        super(id);
    }

}
