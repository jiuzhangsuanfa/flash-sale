package com.jiuzhang.seckill.service.impl;

import com.jiuzhang.seckill.entity.Activity;
import com.jiuzhang.seckill.entity.Order;
import com.jiuzhang.seckill.mapper.ActivityMapper;
import com.jiuzhang.seckill.service.IActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiuzhang.seckill.service.RedisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-01-10
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {
    @Resource
    RedisService redisService;

    /**
     * 查询活动状态查询活动
     * @param activityStatus 活动状态
     * @return 所有状态相同的活动
     */
    @Override
    public List<Activity> getActivitysByStatus(Integer activityStatus) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("activity_status", activityStatus);
        return baseMapper.selectByMap(columnMap);
    }

    /**
     * 处理秒杀请求
     * @param activityId 活动 ID
     * @return 秒杀结果
     */
    @Override
    public String processSeckill(long activityId) {
        Activity activity = baseMapper.selectById(activityId);
        long availableStock = activity.getAvailableStock();
        String result = "";
        if (availableStock > 0) {
            result = "恭喜，抢购成功";
            availableStock = availableStock - 1;
            activity.setAvailableStock(availableStock);
            baseMapper.updateById(activity);
        } else {
            result = "抱歉，抢购失败，商品被抢完了";
        }
        System.out.println(result);
        return result;
    }

    /**
     * 判断商品是否还有库存
     *
     * @param activityId 商品ID
     * @return 是否还有库存
     */
    public boolean stockValidator(long activityId) {
        String key = "stock:" + activityId;
        return redisService.stockDeductValidator(key);
    }

    /**
     * 创建订单，锁定库存
     * @param activityId 活动 ID
     * @return 成功 or 失败
     */
    @Override
    public boolean lockStock(Long activityId) {
        int result = baseMapper.lockStock(activityId );
        if (result < 1) {
            log.error("锁定库存失败");
            return false;
        }
        return true;
    }

    /**
     * 超时未支付，关闭订单，回滚库存
     * @param activityId 活动 ID
     */
    @Override
    public void revertStock(Long activityId) {
       baseMapper.revertStock(activityId);
    }

    /**
     * 扣减库存
     * @param activityId
     */
    @Override
    public void deductStock(Long activityId) {
        baseMapper.deductStock(activityId);
    }
}
