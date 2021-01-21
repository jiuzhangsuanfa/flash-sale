package com.jiuzhang.flashsale.service;

import com.jiuzhang.flashsale.exception.StaticPageGenerateException;

/**
 * 静态页面生成服务
 *
 * @author jiuzhang
 * @since 2021-01-17
 */
public interface StaticPageGenerateService {

    /**
     * 生成秒杀活动的静态页面
     *
     * @param activityId 秒杀活动 ID
     * @throws StaticPageGenerateException 页面生成异常
     */
    public void generateActivityStaticPage(long activityId) throws StaticPageGenerateException;

}
