package com.csu.kafka.consumer;

import com.alibaba.fastjson.JSON;
import com.csu.dto.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class KafkaReceiver {

    @KafkaListener(topics = {"zhisheng"})
    public void listen(ConsumerRecord<?, String> record) {

        Optional<String> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {

            KafkaMessage message = JSON.parseObject(kafkaMessage.get(), KafkaMessage.class);

            System.out.println(System.currentTimeMillis());
            log.info("----------------- record =" + record);
            log.info("------------------ message =" + message);
        }

    }
}