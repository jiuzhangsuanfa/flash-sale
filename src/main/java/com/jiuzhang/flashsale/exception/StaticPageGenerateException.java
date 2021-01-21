package com.jiuzhang.flashsale.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class StaticPageGenerateException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String message;

    public StaticPageGenerateException(String message) {
        this.message = message;
    }

}
