package com.jiuzhang.seckill.controller;


import com.jiuzhang.seckill.entity.Activity;
import com.jiuzhang.seckill.entity.Order;
import com.jiuzhang.seckill.service.IActivityService;
import com.jiuzhang.seckill.service.IOrderService;
import com.jiuzhang.seckill.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-01-10
 */
@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {
    @Resource
    RedisService redisService;

    @Resource
    IActivityService activityService;

    @Resource
    IOrderService orderService;

    /**
     * 处理抢购请求
     * @param activityId
     * @return
     */
    @GetMapping("/{activityId}")
    public ModelAndView seckillCommodity(@PathVariable long activityId) {
        boolean stockValidateResult = false;

        ModelAndView modelAndView = new ModelAndView();
        try {
//            /*
//             * 判断用户是否在已购名单中
//             */
//            if (redisService.isInLimitMember(activityId, 1234)) {
//                //提示用户已经在限购名单中，返回结果
//                modelAndView.addObject("resultInfo", "对不起，您已经在限购名单中");
//                modelAndView.setViewName("seckill_result");
//                return modelAndView;
//            }
            /*
             * 确认是否能够进行秒杀
             */
            stockValidateResult = activityService.stockValidator(activityId);
            if (stockValidateResult) {
                Order order = orderService.createOrder(activityId, 1234);
                modelAndView.addObject("resultInfo","秒杀成功，订单创建中，订单ID：" + order.getId());
                modelAndView.addObject("orderId",order.getId());
            } else {
                modelAndView.addObject("resultInfo","对不起，商品库存不足");
            }
        } catch (Exception e) {
            log.error("秒杀系统异常" + e.toString());
            modelAndView.addObject("resultInfo","秒杀失败");
        }
        modelAndView.setViewName("seckill_result");
        return modelAndView;
    }

    /**
     * 订单查询
     * @param orderId
     * @return
     */
    @RequestMapping("/orderQuery/{orderId}")
    public ModelAndView orderQuery(@PathVariable String orderId) {
        log.info("订单查询，订单号：" + orderId);
        Order order = orderService.getById(orderId);
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
     * 订单支付
     * @return
     */
    @RequestMapping("/payOrder/{orderId}")
    public String payOrder(@PathVariable String orderId) throws Exception {
        orderService.payOrderProcess(orderId);
        return "redirect:/orders/orderQuery/" + orderId;
    }
}
