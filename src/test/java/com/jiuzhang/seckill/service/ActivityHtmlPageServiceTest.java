package com.jiuzhang.seckill.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.jiuzhang.flashsale.service.ActivityHtmlPageService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ActivityHtmlPageServiceTest {

    @Autowired
    private ActivityHtmlPageService activityHtmlPageService;

    @Test
    void testCreateActivityHtml() {
        assertDoesNotThrow(() -> {
            activityHtmlPageService.createActivityHtml(2L);
        });
    }

}
