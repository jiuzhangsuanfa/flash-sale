package com.jiuzhang.seckill.component;


import com.jiuzhang.seckill.entity.Activity;
import com.jiuzhang.seckill.service.IActivityService;
import com.jiuzhang.seckill.service.RedisService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RedisPreheatRunner implements ApplicationRunner {

    @Resource
    RedisService redisService;

    @Resource
    IActivityService activityService;

    /**
     * 将商品库存同步到 redis 中
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args){
        List<Activity> activities = activityService.getActivitysByStatus(1);
        for (Activity activity : activities) {
            redisService.setValue("stock:" + activity.getId(), activity.getAvailableStock());
        }
    }

}
