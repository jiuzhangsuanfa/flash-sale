package com.jiuzhang.flashsale.service;

import com.jiuzhang.flashsale.exception.RedisDistributedLockException;
import com.jiuzhang.flashsale.exception.RedisStockException;
import com.jiuzhang.flashsale.exception.RedisUserException;

/**
 * Redis 服务
 *
 * @author jiuzhang
 * @since 2021-01-17
 */
public interface RedisService {

    /**
     * 添加限购用户
     *
     * @param activityId 活动 ID
     * @param userId     被限购的用户 ID
     */
    void addRestrictedUser(Long activityId, Long userId) throws RedisUserException;

    /**
     * 判断用户是否为限购用户
     *
     * @param activityId 活动 ID
     * @param userId     用户 ID
     * @return 返回是或否
     */
    boolean isRestrictedUser(Long activityId, Long userId) throws RedisUserException;

    /**
     * 解除限购状态
     *
     * @param activityId 活动 ID
     * @param userId     用户 ID
     */
    void removeRestrictedUser(Long activityId, Long userId) throws RedisUserException;

    /**
     * 回滚库存
     *
     * @param activityId 活动 ID
     */
    void revertStock(Long activityId) throws RedisStockException;

    /**
     * 扣减库存
     *
     * @param activityId 活动 ID
     */
    boolean deductStock(Long activityId) throws RedisStockException;

    /**
     * 设置库存
     *
     * @param activityId     活动 ID
     * @param availableStock 可用库存
     */
    void setStock(Long activityId, Long availableStock) throws RedisStockException;

    /**
     * 获取库存
     *
     * @param activityId 活动 ID
     * @return 返回可用库存
     */
    String getStock(Long activityId) throws RedisStockException;

    /**
     * 占用分布式锁资源
     *
     * @param lockKey    锁键
     * @param requestId  请求 ID
     * @param expireTime 超时时间
     */
    void takeUpDistributedLock(String lockKey, String requestId, int expireTime) throws RedisDistributedLockException;

    /**
     * 释放分布式锁资源
     *
     * @param lockKey   锁键
     * @param requestId 请求 ID
     */
    void releaseDistributedLock(String lockKey, String requestId) throws RedisDistributedLockException;

}
