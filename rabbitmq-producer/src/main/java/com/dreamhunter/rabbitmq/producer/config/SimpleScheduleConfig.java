package com.dreamhunter.rabbitmq.producer.config;

import com.dreamhunter.rabbitmq.producer.producer.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class SimpleScheduleConfig {

    @Autowired
    private Sender sender;

    //3.添加定时任务
    @Scheduled(cron = "0/5 * * * * ?")
    private void configureTasks() throws Exception {
        System.out.println("执行定时任务: " + LocalDateTime.now());
        sender.send(LocalDateTime.now().toString());
    }
}
