package com.jiuzhang.flashsale.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum OrderStatus implements IEnum<Integer> {

    NO_STOCK(-1), CLOSED(0), CREATED(1), PAID(2);

    private Integer status;

    private OrderStatus(int status) {
        this.status = status;
    }

    @Override
    public Integer getValue() {
        return this.status;
    }

}
