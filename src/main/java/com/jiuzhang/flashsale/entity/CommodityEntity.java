package com.jiuzhang.flashsale.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 秒杀商品 Entity
 *
 * @author jiuzhang
 * @since 2021-01-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CommodityEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;

    private String commodityName;

    private String commodityDesc;

    private BigDecimal commodityPrice;

}
