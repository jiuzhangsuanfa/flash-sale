package com.jiuzhang.seckill;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Date;

import com.jiuzhang.flashsale.service.RocketMQService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MQTest {

    @Autowired
    RocketMQService rocketMQService;

    @Test
    void testSendMessage() {
        assertDoesNotThrow(() -> {
            rocketMQService.sendMessage("test-jiuzhang", "Hello World!" + new Date().toString());
        });
    }

}
