package com.jiuzhang.flashsale.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiuzhang.flashsale.entity.Order;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jiuzhang
 * @since 2021-01-15
 */
public interface IOrderService extends IService<Order> {

    Order createOrder(long activityId, int userId) throws Exception;

    void payOrderProcess(String orderId) throws Exception;
}
