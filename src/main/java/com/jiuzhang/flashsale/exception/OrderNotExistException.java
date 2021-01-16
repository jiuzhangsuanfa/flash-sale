package com.jiuzhang.flashsale.exception;

public class OrderNotExistException extends OrderException {

    private static final long serialVersionUID = 1L;

    public OrderNotExistException(String id) {
        super(id);
    }

}
