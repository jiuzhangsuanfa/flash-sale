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

    /**
     * 创建订单
     *
     * @param activityId 订单 ID
     * @param userId
     * @return 返回订单信息
     * @throws OrderCreateException   订单创建异常
     * @throws OrderPayCheckException 订单超时支付检查异常
     */
    OrderEntity createOrder(long activityId, int userId) throws OrderCreateException, OrderPayCheckException;

    /**
     * 支付订单
     *
     * @param orderId 订单 ID
     * @return 返回订单信息
     * @throws OrderNotExistException 订单不存在
     * @throws OrderInvalidException  无效订单异常
     * @throws OrderPayException      订单支付异常
     */
    OrderEntity payOrder(String orderId) throws OrderNotExistException, OrderInvalidException, OrderPayException;

    /**
     * 关闭订单
     *
     * @param orderId 订单 ID
     * @return 返回订单信息
     * @throws OrderNotExistException 订单不存在异常
     */
    OrderEntity closeOrder(String orderId) throws OrderNotExistException, OrderInvalidException;

}
