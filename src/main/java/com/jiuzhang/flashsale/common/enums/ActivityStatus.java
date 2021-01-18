package com.jiuzhang.flashsale.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum ActivityStatus implements IEnum<Integer> {

    NORMAL(0), END(1);

    private int status;

    private ActivityStatus(int status) {
        this.status = status;
    }

    @Override
    public Integer getValue() {
        return this.status;
    }

}
