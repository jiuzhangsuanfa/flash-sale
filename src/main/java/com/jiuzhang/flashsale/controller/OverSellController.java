package com.jiuzhang.flashsale.controller;

import javax.annotation.Resource;

import com.jiuzhang.flashsale.service.ActivityService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("oversell")
public class OverSellController {

    @Resource
    ActivityService activityService;

    @PostMapping("{id}")
    public boolean oversell(@PathVariable long activityId, @RequestParam String type) {
        if (type.equals("OVERSELL")) {
            return this.processOverSell(activityId);
        }
        return this.processOverSell(activityId);
    }

    /**
     * 简单 库存扣减处理
     *
     * @param activityId 秒杀活动 ID
     * @return 处理结果
     */
    public boolean processOverSell(long activityId) {
        return activityService.processOverSell(activityId);
    }

    /**
     * 使用 lua 脚本处理库存扣减
     *
     * @param activityId 秒杀活动 ID
     * @return 处理结果
     */
    public boolean processLua(long activityId) {
        return activityService.hasStock(activityId);
    }

}
