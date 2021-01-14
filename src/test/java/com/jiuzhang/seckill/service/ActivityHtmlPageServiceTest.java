package com.jiuzhang.seckill.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ActivityHtmlPageServiceTest {

    @Autowired
    private ActivityHtmlPageService activityHtmlPageService;

    @Test
    void createActivityHtml() {
        activityHtmlPageService.createActivityHtml(2L);
    }
}