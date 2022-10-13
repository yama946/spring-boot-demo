package com.yama.demo.rabbitmq.controller;

import com.yama.demo.rabbitmq.entity.Person;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @description:
 * @date: 2022年10月13日 周四 17:14
 * @author: yama946
 */
@RestController
public class SendMessageController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public String sendMessage(@RequestParam(value = "num",defaultValue = "10") Integer num){
        for (int i = 0; i < num; i++) {
            Person person = new Person("yama"+i, 23, 173.43d, 65.23d, "河南郑州金水区");
            rabbitTemplate.convertAndSend("demo.exchange.direct","demo.queue",
                    person,new CorrelationData(UUID.randomUUID().toString()));
        }
        return "send message......ok";
    }

    @GetMapping("/error/queue/{num}")
    public String sendMessageQueue(@PathVariable("num") Integer num){
        for (int i = 0; i < num; i++) {
            Person person = new Person("yama"+i, 23, 173.43d, 65.23d, "河南郑州金水区");
            //发送消息时指定消息的唯一ID
            rabbitTemplate.convertAndSend("demo.exchange.direct","demo.queue.error",person,
                            new CorrelationData(UUID.randomUUID().toString()));
        }
        return "send message queue error......ok";
    }
}
