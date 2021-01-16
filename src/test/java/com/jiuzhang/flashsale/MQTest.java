package com.jiuzhang.flashsale;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Date;

import com.jiuzhang.flashsale.service.impl.RocketMQServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MQTest {

    @Autowired
    RocketMQServiceImpl rocketMQService;

    @Test
    void testSendMessage() {
        assertDoesNotThrow(() -> {
            rocketMQService.sendMessage("test-jiuzhang", "Hello World!" + new Date().toString());
        });
    }

}
