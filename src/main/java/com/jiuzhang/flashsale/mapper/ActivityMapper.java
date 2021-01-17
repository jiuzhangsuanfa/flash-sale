package com.jiuzhang.flashsale.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiuzhang.flashsale.entity.ActivityEntity;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jiuzhang
 * @since 2021-01-15
 */
public interface ActivityMapper extends BaseMapper<ActivityEntity> {

    int lockStock(Long activityId);

    int revertStock(Long activityId);

    int deductStock(Long activityId);

}
