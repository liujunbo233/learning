package com.csu.rocketmq;

import com.alibaba.fastjson.JSON;
import com.csu.dto.OrderPaidEvent;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "test-topic-2", consumerGroup = "my-consumer_test-topic-2")
public class OrderPaidEventConsumer implements RocketMQListener<OrderPaidEvent> {

    @Override
    public void onMessage(OrderPaidEvent message) {

        System.out.print("------- OrderPaidEventConsumer received:"+ JSON.toJSONString(message));
    }

}
