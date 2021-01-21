package com.jiuzhang.flashsale.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StaticPageGenerateServiceTest {

    @Autowired
    private StaticPageGenerateService staticPageGenerateService;

    @Test
    void testCreateActivityHtml() {
        assertDoesNotThrow(() -> {
            staticPageGenerateService.generateActivityStaticPage(2L);
        });
    }

}
