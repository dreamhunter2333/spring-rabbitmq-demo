package com.dreamhunter.rabbitmq.client.pojo;

import com.dreamhunter.rabbitmq.client.config.RabbitmqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;

    public Runner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(1000);
            System.out.println("Sending message..." + i);
            rabbitTemplate.convertAndSend(
                    RabbitmqConfig.topicExchangeName,
                    "foo.bar.baz", "Hello from RabbitMQ!" + i
            );
        }
    }

}