package com.jiuzhang.flashsale.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author jobob
 * @since 2021-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Activity implements Serializable {

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
     * 秒杀活动的状态，0:下架 1:正常
     */
    private Integer activityStatus;

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
