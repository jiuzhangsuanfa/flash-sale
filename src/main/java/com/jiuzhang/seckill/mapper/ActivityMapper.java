package com.jiuzhang.seckill.mapper;

import com.jiuzhang.seckill.entity.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-01-10
 */
public interface ActivityMapper extends BaseMapper<Activity> {

    int lockStock(Long activityId);

    int revertStock(Long activityId);

    void deductStock(Long activityId);
}
