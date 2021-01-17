package com.jiuzhang.flashsale.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiuzhang.flashsale.entity.ActivityEntity;
import com.jiuzhang.flashsale.mapper.ActivityMapper;
import com.jiuzhang.flashsale.service.ActivityService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jiuzhang
 * @since 2021-01-15
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, ActivityEntity> implements ActivityService {

    @Resource
    RedisServiceImpl redisService;

    /**
     * 查询活动状态查询活动
     *
     * @param activityStatus 活动状态
     * @return 所有状态相同的活动
     */
    @Override
    public List<ActivityEntity> getActivitiesByStatus(Integer activityStatus) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("activity_status", activityStatus);
        return baseMapper.selectByMap(columnMap);
    }

    /**
     * 处理秒杀请求
     *
     * @param activityId 活动 ID
     * @return 秒杀结果
     */
    @Override
    public boolean processOverSell(long activityId) {
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

    /**
     * 判断商品是否还有库存
     *
     * @param activityId 商品ID
     * @return 是否还有库存
     */
    @Override
    public boolean hasStock(long activityId) {
        String key = "stock:" + activityId;
        return redisService.stockDeductValidator(key);
    }

    /**
     * 创建订单，锁定库存
     *
     * @param activityId 活动 ID
     * @return 成功 or 失败
     */
    @Override
    public boolean lockStock(Long activityId) {
        return baseMapper.lockStock(activityId) >= 1;
    }

    /**
     * 超时未支付，关闭订单，回滚库存
     *
     * @param activityId 活动 ID
     */
    @Override
    public boolean revertStock(Long activityId) {
        return baseMapper.revertStock(activityId) >= 1;
    }

    /**
     * 扣减库存
     *
     * @param activityId 活动 ID
     */
    @Override
    public boolean deductStock(Long activityId) {
        return baseMapper.deductStock(activityId) >= 1;
    }

}
