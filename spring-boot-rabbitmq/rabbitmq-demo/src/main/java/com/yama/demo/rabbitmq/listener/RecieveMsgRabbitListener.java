package com.yama.demo.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import com.yama.demo.rabbitmq.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 1.@RabbitListener：类+方法---->声明监听那些队列
 *      - queues属性：指定监听的队列
 *      - 需要@EnableRabbit注解开启的支持
 *      - 可以直接作用到方法上，表示该方法进行接收消息
 *
 * 2. @RabbitHandler：方法上------>重载区分不同的消息
 *      - 当队列中存在多种消息类型，不能使用一个方法进行接收，就需要使用该注解
 *      - 该注解标注方法为消息监听器，具体那个方法处理消息，取决于消息的类型是否与方法接收类型匹配
 *
 * 注意：如果出现，多个监听方法监听同一类型的数据（一个接收Object，一个接收Person，领域重叠），将会异常
 *
 * @description: 监听队列中消息，并进行接收
 * @date: 2022年10月13日 周四 17:02
 * @author: yama946
 *
 *
 * TODO 队列可以被很多消费者进行监听，但是消息只能有一个接收成功，因为消息只有一个。
 * TODO 消费者消费消息，只能一个处理结束后才能接收下一个进行处理。
 * 默认情况下，接收到消息后会自动确认。
 * 场景：
 *      发送多个消息：
 *          1.将当前服务进行复制配置，形成集群进行监听消息，观察同一个消息是否只能被一个消费者消费。
 *          2.当一个消费者消费一个消息，会不会同时消费另一个消息。
 */
@Slf4j
@RabbitListener(queues = "demo.queue")
@Component
public class RecieveMsgRabbitListener {
    /**
     * 接收方法可以写的参数：
     *      1.Message message
     *      2.发送消息的类型
     *      3.Channel channel:当前传输数据的通道
     *
     * @param person  直接使用消息对应的对象类型接收，会自动转换包装
     * @param message 原生消息类型，包含头+体，其中体就是对象json数据，可以获取后转换
     *                org.springframework.amqp.core.Message是直接从通道中获取未处理的原生数据
     */
    @RabbitHandler
    public void recieveMessage(Person person, Message message, Channel channel) throws InterruptedException, IOException {
        //使用发送消息类型，直接接收消息
        log.debug(person.toString());
        log.debug("原生消息整体："+message);
        byte[] body = message.getBody();
        log.debug("原生消息体:"+new String(body));
        MessageProperties messageProperties = message.getMessageProperties();
        log.debug("原生消息参数："+messageProperties);
        //模拟消息处理过程
        Thread.sleep(10000l);
        log.info("消息处理结束");
        /**
         * 使用Channel进行手动签收消息
         */
        //deliveryTag标签，在Channel中的消息顺序标志，是自增的
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {

            /**
             * 1.签收货物
             *      void basicAck(long deliveryTag, boolean multiple)，签收消息
             *      参数：
             *          deliveryTag：表示消息在通道内的标号
             *          multiple：是否批量确认，也就确认通道内所有消息，false表示只确认当前消息
             */
            //签收获取
            channel.basicAck(deliveryTag,false);
        }catch (Exception e){
            //网络中断异常
            /**
             * 2.拒签货物
             *      void basicReject(long deliveryTag, boolean requeue)，//拒签消息，不可批量拒绝
             *      void basicNack(long deliveryTag, boolean multiple, boolean requeue)//拒签消息，可批量拒绝
             *      参数：
             *          requeue：是否重新入队
             *                   true表示消息重新入队，与不签收消息效果相同
             *                   false表示不重新入队，将拒签的消息丢弃
             */
            channel.basicReject(deliveryTag,true);
            channel.basicNack(deliveryTag,false,true);
        }


    }

    @RabbitHandler
    public void recieveMessageOne(String body){

    }

}
