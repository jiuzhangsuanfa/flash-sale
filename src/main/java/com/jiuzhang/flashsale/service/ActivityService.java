package com.jiuzhang.flashsale.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiuzhang.flashsale.common.enums.ActivityStatus;
import com.jiuzhang.flashsale.entity.ActivityEntity;
import com.jiuzhang.flashsale.exception.RedisStockException;

/**
 * 秒杀活动服务
 *
 * @author jiuzhang
 * @since 2021-01-15
 */
public interface ActivityService extends IService<ActivityEntity> {

    /**
     * 查询所有符合状态的秒杀活动
     *
     * @param activityStatus 活动状态
     * @return 所有符合状态的秒杀活动
     */
    List<ActivityEntity> getActivitiesByStatus(ActivityStatus activityStatus);

    /**
     * 超卖库存演示
     *
     * @param activityId 活动 ID
     * @return 返回超卖演示结果
     */
    boolean oversellStock(long activityId);

    /**
     * 判断是否有库存
     *
     * @param activityId 活动 ID
     * @return 返回是否有库存
     * @throws RedisStockException
     */
    boolean hasStock(long activityId) throws RedisStockException;

    /**
     * 锁定库存
     *
     * @param activityId 活动 ID
     * @return 返回锁定结果
     */
    boolean lockStock(Long activityId);

    /**
     * 回滚库存
     *
     * @param activityId 活动 ID
     * @return 返回回滚结果
     */
    boolean revertStock(Long activityId);

    /**
     * 扣减库存
     *
     * @param activityId 活动 ID
     * @return 返回扣减结果
     */
    boolean deductStock(Long activityId);

}
