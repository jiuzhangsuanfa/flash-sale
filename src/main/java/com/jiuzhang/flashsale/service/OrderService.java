package com.jiuzhang.flashsale.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiuzhang.flashsale.entity.OrderEntity;
import com.jiuzhang.flashsale.exception.OrderCreateException;
import com.jiuzhang.flashsale.exception.OrderInvalidException;
import com.jiuzhang.flashsale.exception.OrderNotExistException;
import com.jiuzhang.flashsale.exception.OrderPayCheckException;
import com.jiuzhang.flashsale.exception.OrderPayException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jiuzhang
 * @since 2021-01-15
 */
public interface OrderService extends IService<OrderEntity> {

    OrderEntity createOrder(long activityId, int userId) throws OrderCreateException, OrderPayCheckException;

    OrderEntity payOrderProcess(String orderId) throws OrderNotExistException, OrderInvalidException, OrderPayException;

}
