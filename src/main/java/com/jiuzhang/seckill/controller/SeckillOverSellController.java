package com.jiuzhang.seckill.controller;

import com.jiuzhang.seckill.service.IActivityService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SeckillOverSellController {

    @Resource
    IActivityService activityService;

    /**
     * 简单 库存扣减处理
     *
     * @param activityId
     * @return 处理结果
     */
    @RequestMapping("/test/{activityId}")
    public String processSeckill(@PathVariable long activityId) {
        return activityService.processSeckill(activityId);
    }

    /**
     * 使用 lua 脚本处理库存扣减
     *
     * @param activityId
     * @return
     */
    @RequestMapping("/testLua/{activityId}")
    public String testLua(@PathVariable long activityId) {
        boolean stockValidateResult = activityService.stockValidator(activityId);
        return stockValidateResult ? "恭喜你秒杀成功" : "商品已经售完，下次再来";
    }
}
