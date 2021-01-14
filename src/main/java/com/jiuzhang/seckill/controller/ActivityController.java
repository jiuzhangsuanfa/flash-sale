package com.jiuzhang.seckill.controller;


import com.jiuzhang.seckill.entity.Activity;
import com.jiuzhang.seckill.entity.Commodity;
import com.jiuzhang.seckill.mapper.ActivityMapper;
import com.jiuzhang.seckill.service.IActivityService;
import com.jiuzhang.seckill.service.ICommodityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-01-10
 */
@Controller
@RequestMapping("/activitys")
public class ActivityController {
    @Resource
    IActivityService activityService;

    @Resource
    ICommodityService commodityService;

    /**
     * 跳转活动详情页面
     *
     * @param resultMap  存储传给前端的数据
     * @param activityId 活动 ID
     * @return 活动详情页面
     */
    @RequestMapping("/{activityId}")
    public String itemPage(Map<String, Object> resultMap, @PathVariable long activityId) {
        Activity activity = activityService.getById(activityId);
        Commodity commodity = commodityService.getById(activity.getCommodityId());

        resultMap.put("activity", activity);
        resultMap.put("commodity", commodity);
        return "seckill_item";
    }

    /**
     * 所有活动的列表页
     *
     * @param resultMap 存储传给前端的数据
     * @return 活动的列表页面
     */
    @GetMapping
    public String activitys(Map<String, Object> resultMap) {
        // 获取活动状态为 1 的活动
        List<Activity> activityList = activityService.getActivitysByStatus(1);
        resultMap.put("activityList", activityList);
        return "activity_list";
    }

    /**
     * 发布活动功能
     *
     * @param activity  接收 from 表单提交的数据
     * @param resultMap 存储传给前端的数据
     * @return 添加成功的页面
     */
    @PostMapping
    public String addActivity(Activity activity, Map<String, Object> resultMap) {
        activity.setAvailableStock(activity.getTotalStock());
        activity.setLockStock(0L);
        activity.setActivityStatus(1);
        activityService.save(activity);
        resultMap.put("activity", activity);
        return "add_success";
    }

    /**
     * 跳转活动发布页面
     *
     * @return 活动页面名称
     */
    @RequestMapping("/add_activity.html")
    public String addSeckillActivity() {
        return "add_activity";
    }
}
