package com.csu.rocketmq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "rocket-mq-transaction", consumerGroup = "transaction")
public class OrderConsumer implements RocketMQListener<Object> {

    @Override
    public void onMessage(Object message) {

        System.out.println("接收到事务消息");
    }

}
