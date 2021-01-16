package com.jiuzhang.flashsale.component;

import java.util.List;

import javax.annotation.Resource;

import com.jiuzhang.flashsale.entity.Activity;
import com.jiuzhang.flashsale.service.ActivityService;
import com.jiuzhang.flashsale.service.RedisService;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RedisPreheatRunner implements ApplicationRunner {

    @Resource
    RedisService redisService;

    @Resource
    ActivityService activityService;

    /**
     * 将商品库存同步到 redis 中
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) {
        List<Activity> activities = activityService.getActivitiesByStatus(1);
        for (Activity activity : activities) {
            redisService.setValue("stock:" + activity.getId(), activity.getAvailableStock());
        }
    }

}
