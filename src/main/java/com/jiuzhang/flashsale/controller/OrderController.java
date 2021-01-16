package com.jiuzhang.flashsale.controller;

import javax.annotation.Resource;

import com.jiuzhang.flashsale.entity.Activity;
import com.jiuzhang.flashsale.entity.Order;
import com.jiuzhang.flashsale.service.ActivityService;
import com.jiuzhang.flashsale.service.OrderService;
import com.jiuzhang.flashsale.service.RedisService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jiuzhang
 * @since 2021-01-15
 */
@Slf4j
@Controller
@RequestMapping("orders")
public class OrderController {
    @Resource
    RedisService redisService;

    @Resource
    ActivityService activityService;

    @Resource
    OrderService orderService;

    /**
     * 根据秒杀活动 ID 创建订单
     *
     * @param activityId 秒杀活动 ID
     * @return
     */
    @PostMapping
    public ModelAndView seckillCommodity(@RequestParam long activityId) {
        boolean stockValidateResult = false;
        ModelAndView modelAndView = new ModelAndView();
        String infoName = "resultInfo";
        try {
            /** 确认是否能够进行秒杀 */
            stockValidateResult = activityService.hasStock(activityId);
            if (stockValidateResult) {
                Order order = orderService.createOrder(activityId, 1234);
                modelAndView.addObject(infoName, "秒杀成功，订单创建中，订单ID：" + order.getId());
                modelAndView.addObject("orderId", order.getId());
            } else {
                modelAndView.addObject(infoName, "对不起，商品库存不足");
            }
        } catch (Exception e) {
            log.error("秒杀系统异常" + e.toString());
            modelAndView.addObject(infoName, "秒杀失败");
        }
        modelAndView.setViewName("seckill_result");
        return modelAndView;
    }

    /**
     * 订单查询
     *
     * @param id 订单 ID
     * @return
     */
    @GetMapping("{id}")
    public ModelAndView orderQuery(@PathVariable String id) {
        log.info("订单查询，订单号：" + id);
        Order order = orderService.getById(id);
        ModelAndView modelAndView = new ModelAndView();
        if (order != null) {
            modelAndView.setViewName("order");
            modelAndView.addObject("order", order);
            Activity activity = activityService.getById(order.getActivityId());
            modelAndView.addObject("activity", activity);
        } else {
            modelAndView.setViewName("order_wait");
        }
        return modelAndView;
    }

    /**
     * 支付订单
     *
     * @param id 订单 ID
     * @return
     * @throws Exception
     */
    @PostMapping("{id}")
    public String payOrder(@PathVariable String id) throws Exception {
        orderService.payOrderProcess(id);
        return "redirect:/orders/orderQuery/" + id;
    }
}
