package com.jiuzhang.flashsale.service;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.jiuzhang.flashsale.entity.Activity;
import com.jiuzhang.flashsale.entity.Commodity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActivityHtmlPageService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private ICommodityService commodityService;

    /**
     * 创建html页面
     *
     * @throws Exception
     */
    public void createActivityHtml(long activityId) {

        PrintWriter writer = null;
        try {
            Activity activity = activityService.getById(activityId);
            Commodity commodity = commodityService.getById(activity.getCommodityId());
            // 获取页面数据
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("activity", activity);
            resultMap.put("commodity", commodity);
            // resultMap.put("seckillPrice", seckillActivity.getSeckillPrice());
            // resultMap.put("oldPrice", seckillActivity.getOldPrice());
            // resultMap.put("commodityId", seckillActivity.getCommodityId());
            // resultMap.put("commodityName", seckillCommodity.getCommodityName());
            // resultMap.put("commodityDesc", seckillCommodity.getCommodityDesc());

            // 创建thymeleaf上下文对象
            Context context = new Context();
            // 把数据放入上下文对象
            context.setVariables(resultMap);

            // 创建输出流
            File file = new File("src/main/resources/templates/" + "seckill_item_" + activityId + ".html");
            writer = new PrintWriter(file);
            // 执行页面静态化方法
            templateEngine.process("seckill_item", context, writer);
        } catch (Exception e) {
            log.error(e.toString());
            log.error("页面静态化出错：" + activityId);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
