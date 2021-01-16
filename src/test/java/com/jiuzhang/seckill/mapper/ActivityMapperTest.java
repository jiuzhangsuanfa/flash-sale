package com.jiuzhang.seckill.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.annotation.Resource;

import com.jiuzhang.flashsale.entity.Activity;
import com.jiuzhang.flashsale.mapper.ActivityMapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        assertEquals(1, result);
    }

}
