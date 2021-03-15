package com.dreamhunter.rabbitmq.producer.producer;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String str) throws Exception {
        System.out.println("发送消息：" + str);
        // 通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，也就是只确认是否正确到达 Exchange 中
//        rabbitTemplate.setConfirmCallback(confirmCallback);


        // 消息是否成功发送到Exchange
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                String messageId = correlationData.getId();
                System.out.println("回调成功，消息 id: " + messageId);
            } else {
                System.err.println("异常处理...");
            }
        });
        //消息唯一ID
        CorrelationData correlationData = new CorrelationData(str);
        rabbitTemplate.convertAndSend(
                "spring-boot-exchange",
                "demo.demo",
                "Hello from RabbitMQ!" + str,
                correlationData
        );
    }

}