package com.jiuzhang.flashsale.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.jiuzhang.flashsale.entity.ActivityEntity;
import com.jiuzhang.flashsale.entity.CommodityEntity;
import com.jiuzhang.flashsale.enums.ActivityStatus;
import com.jiuzhang.flashsale.service.ActivityService;
import com.jiuzhang.flashsale.service.CommodityService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 秒杀活动
 *
 * @author jiuzhang
 * @since 2021-01-15
 */
@Controller
@RequestMapping("activities")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @Resource
    private CommodityService commodityService;

    /**
     * 查询活动详情
     *
     * @param resultMap thymeleaf 模板数据
     * @param id        活动 ID
     * @return 活动详情页面
     */
    @GetMapping("{id}")
    public String itemPage(Map<String, Object> resultMap, @PathVariable long id) {
        ActivityEntity activity = activityService.getById(id);
        CommodityEntity commodity = commodityService.getById(activity.getCommodityId());
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
        List<ActivityEntity> activityList = activityService.getActivitiesByStatus(1);
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
    public String addActivity(ActivityEntity activity, Map<String, Object> resultMap) {
        activity.setAvailableStock(activity.getTotalStock());
        activity.setLockStock(0L);
        activity.setActivityStatus(ActivityStatus.NORMAL);
        activityService.save(activity);
        resultMap.put("activity", activity);
        return "add_success";
    }

    /**
     * 跳转活动发布页面
     *
     * @return 活动页面名称
     */
    @GetMapping("add_activity.html")
    public String addSeckillActivity() {
        return "add_activity";
    }

}
