package com.jiuzhang.flashsale.component;

import java.util.List;

import javax.annotation.Resource;

import com.jiuzhang.flashsale.common.enums.ActivityStatus;
import com.jiuzhang.flashsale.entity.ActivityEntity;
import com.jiuzhang.flashsale.exception.RedisStockException;
import com.jiuzhang.flashsale.service.ActivityService;
import com.jiuzhang.flashsale.service.impl.RedisServiceImpl;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

/**
 * 商品预热，从数据库到 Redis 缓存
 *
 * @author jiuzhang
 * @since 2021-01-17
 */
@Log4j2
@Component
public class RedisPreheatRunner implements ApplicationRunner {

    @Resource
    RedisServiceImpl redisService;

    @Resource
    ActivityService activityService;

    /**
     * 将商品库存同步到 redis 中
     *
     * @param args
     */
    @Override
    public void run(ApplicationArguments args) {
        List<ActivityEntity> activities = activityService.getActivitiesByStatus(ActivityStatus.NORMAL);
        for (ActivityEntity activity : activities) {
            try {
                redisService.setStock(activity.getId(), activity.getAvailableStock());
            } catch (RedisStockException exception) {
                log.warn("preheat failed with id:" + activity.getId() + "\n" + exception.toString());
            }
        }
    }

}
