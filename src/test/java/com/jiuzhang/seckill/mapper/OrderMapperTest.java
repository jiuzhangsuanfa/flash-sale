package com.jiuzhang.seckill.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.annotation.Resource;

import com.jiuzhang.seckill.entity.Order;
import com.jiuzhang.seckill.util.SnowFlake;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderMapperTest {

    @Resource
    OrderMapper orderMapper;

    @Test
    void testInsert() {
        Order order = new Order();
        order.setId(new SnowFlake(1, 1).nextId() + "");
        order.setActivityId(2L);
        order.setUserId((long) 12345);
        order.setOrderAmount(new BigDecimal(123456.34));
        order.setOrderStatus(1);
        order.setCreateTime(LocalDateTime.now());
        assertEquals(1, orderMapper.insert(order));
    }

}
