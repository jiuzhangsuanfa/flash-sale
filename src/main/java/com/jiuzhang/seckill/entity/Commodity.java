package com.jiuzhang.seckill.entity;

import java.math.BigDecimal;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class Commodity implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;

    private String commodityName;

    private String commodityDesc;

    private BigDecimal commodityPrice;


}
