package com.dreamhunter.rabbitmq.consumer.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class Receiver {
    //配置监听的哪一个队列，同时在没有queue和exchange的情况下会去创建并建立绑定关系
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring-boot", durable = "true"),
            exchange = @Exchange(name = "spring-boot-exchange", durable = "true", type = "topic"),
            key = "demo.*"
    )
    )
    @RabbitHandler//如果有消息过来，在消费的时候调用这个方法
    public void onOrderMessage(@Payload String str, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        //消费者操作
        System.out.println("---------收到消息，开始消费---------");
        System.out.println(str);

        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        //ACK,确认一条消息已经被消费
        channel.basicAck(deliveryTag, false);
        System.out.println("---------确认消息，已被消费---------");
    }
}