package com.csu.rocketmq;

import com.csu.dto.OrderPaidEvent;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Component
public class RocketMqTest implements CommandLineRunner {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void run(String... args) throws Exception {

        String name = "aaa";
        rocketMQTemplate.convertAndSend("test-topic-1", name);
        rocketMQTemplate.send("test-topic-2", MessageBuilder.withPayload(new OrderPaidEvent("aa,22",new BigDecimal("22"))).build());

        System.err.println("发送成功...");

    }
}
