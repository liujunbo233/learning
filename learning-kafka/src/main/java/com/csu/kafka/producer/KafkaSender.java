package com.csu.kafka.producer;

import com.alibaba.fastjson.JSON;
import com.csu.dto.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    //发送消息方法
    public void send() throws ExecutionException, InterruptedException {
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setId(System.currentTimeMillis());
        kafkaMessage.setMsg(UUID.randomUUID().toString());
        kafkaMessage.setSendTime(new Date());
        log.info("+++++++++++++++++++++  message = {}", JSON.toJSONString(kafkaMessage));
        System.out.println(System.currentTimeMillis());
        kafkaTemplate.send("zhisheng", JSON.toJSONString(kafkaMessage)).get();
    }
}