package com.jiuzhang.flashsale.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiuzhang.flashsale.entity.Activity;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jiuzhang
 * @since 2021-01-15
 */
public interface ActivityMapper extends BaseMapper<Activity> {

    int lockStock(Long activityId);

    int revertStock(Long activityId);

    int deductStock(Long activityId);

}
