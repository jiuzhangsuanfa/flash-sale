package com.jiuzhang.flashsale.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jiuzhang.flashsale.common.enums.ActivityStatus;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 秒杀活动 Entity
 *
 * @author jiuzhang
 * @since 2021-01-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActivityEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 秒杀活动名称
     */
    private String name;

    private Long commodityId;

    /**
     * 商品原价
     */
    private BigDecimal oldPrice;

    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;

    /**
     * 秒杀活动的状态 0 正常 1 结束
     */
    private ActivityStatus activityStatus;

    /**
     * 活动开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;

    /**
     * 秒杀活动库存
     */
    private Long totalStock;

    /**
     * 可用库存
     */
    private Long availableStock;

    /**
     * 锁定库存数量
     */
    private Long lockStock;

}
