package com.jiuzhang.flashsale.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiuzhang.flashsale.entity.Activity;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jobob
 * @since 2021-01-10
 */
public interface IActivityService extends IService<Activity> {

    public List<Activity> getActivitysByStatus(Integer activityStatus);

    public String processSeckill(long activityId);

    public boolean stockValidator(long activityId);

    boolean lockStock(Long activityId);

    void revertStock(Long activityId);

    void deductStock(Long activityId);
}
