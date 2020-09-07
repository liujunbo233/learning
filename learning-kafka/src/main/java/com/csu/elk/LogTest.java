package com.csu.elk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogTest implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

        log.info("打印启动日志，时间:{}",System.currentTimeMillis());

    }

//    public static void main(String[] args) {
//        System.out.println("123");
//    }
}
