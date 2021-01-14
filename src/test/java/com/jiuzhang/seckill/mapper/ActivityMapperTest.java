package com.jiuzhang.seckill.mapper;

import com.jiuzhang.seckill.entity.Activity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ActivityMapperTest {
    @Resource
    ActivityMapper activityMapper;

    @Test
    void testInsert() {
        Activity activity = new Activity();
        activity.setName("测试");
        activity.setCommodityId(1L);
        activity.setActivityStatus(1);
        activity.setSeckillPrice(new BigDecimal(99));
        activity.setOldPrice(new BigDecimal(99));
        activity.setTotalStock(100L);
        activity.setAvailableStock(100L);
        activity.setLockStock(0L);
        activity.setStartTime(LocalDateTime.now());
        activity.setEndTime(LocalDateTime.now());
        int result = activityMapper.insert(activity);
        Assertions.assertEquals(1 ,result);
    }

}