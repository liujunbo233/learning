package com.csu.kafka;

import com.csu.kafka.producer.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class KafkaBoot implements CommandLineRunner {

    @Autowired
    KafkaSender kafkaSender;
    @Override
    public void run(String... args) throws Exception {
        kafkaSender.send();
    }
}
