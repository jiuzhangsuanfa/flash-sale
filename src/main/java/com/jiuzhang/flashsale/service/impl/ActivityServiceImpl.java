package com.jiuzhang.flashsale.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiuzhang.flashsale.entity.ActivityEntity;
import com.jiuzhang.flashsale.enums.ActivityStatus;
import com.jiuzhang.flashsale.exception.RedisStockException;
import com.jiuzhang.flashsale.mapper.ActivityMapper;
import com.jiuzhang.flashsale.service.ActivityService;

import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, ActivityEntity> implements ActivityService {

    @Resource
    RedisServiceImpl redisService;

    @Override
    public List<ActivityEntity> getActivitiesByStatus(ActivityStatus activityStatus) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("activity_status", activityStatus);
        return baseMapper.selectByMap(columnMap);
    }

    @Override
    public boolean oversellStock(long activityId) {
        ActivityEntity activity = baseMapper.selectById(activityId);
        long availableStock = activity.getAvailableStock();
        if (availableStock > 0) {
            availableStock = availableStock - 1;
            activity.setAvailableStock(availableStock);
            baseMapper.updateById(activity);
            return true;
        }
        return false;
    }

    @Override
    public boolean hasStock(long activityId) throws RedisStockException {
        return redisService.deductStock(activityId);
    }

    @Override
    public boolean lockStock(Long activityId) {
        return baseMapper.lockStock(activityId) >= 1;
    }

    @Override
    public boolean revertStock(Long activityId) {
        return baseMapper.revertStock(activityId) >= 1;
    }

    @Override
    public boolean deductStock(Long activityId) {
        return baseMapper.deductStock(activityId) >= 1;
    }

}
