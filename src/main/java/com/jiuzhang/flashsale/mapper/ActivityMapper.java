package com.jiuzhang.flashsale.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiuzhang.flashsale.entity.Activity;

/**
 * <p>
 * Mapper 接口
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
