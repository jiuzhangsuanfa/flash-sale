package com.jiuzhang.seckill.service;

import com.jiuzhang.seckill.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-01-10
 */
public interface IOrderService extends IService<Order> {

    Order createOrder(long activityId, int userId) throws Exception;

    void payOrderProcess(String orderId) throws Exception;
}
