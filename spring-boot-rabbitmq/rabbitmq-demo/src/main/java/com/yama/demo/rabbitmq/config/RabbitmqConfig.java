package com.yama.demo.rabbitmq.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @date: 2022年10月13日 周四 16:44
 * @author: yama946
 */
@EnableRabbit
@Configuration
public class RabbitmqConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 使用JSON序列化机制，进行消息转换
     * @return
     */
    @Bean
    public MessageConverter getMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }


    /**
     * 定制RabbitTemplate,设置setConfirmCallback回调函数。
     *
     * 1、当消息代理收到消息的回调
     *      步骤：
     *          1.# 开启发送端确认： spring.rabbitmq.publisher-confirms=true
     *          2.设置确认回调方法
     * 2. 消息未抵达指定的队列的回调
     *      步骤：
     *          1.# 开启发送端消息抵达队列的确认：spring.rabbitmq.publisher-returns=true
     *           # 只要抵达队列，以异步发送优先回调returnConfirm：spring.rabbitmq.template.mandatory=true
     *          2.设置回调方法
     * 3.消费端确认。(保证每个消息被正确消费，此时broker才可以删除这个消息)
     *          1.默认是自动确认的，只要消息接收到，客户端会自动确认，服务端会移除这个消息。
     *              问题：
     *                      可能导致消息丢失。
     *              解决方法： 开启手动确认模式：spring.rabbitmq.listener.simple.acknowledge-mode=manual
     *                      手动确认模式，只要我们没有明确的告诉broker(rabbitmq server)，货物被签收，就没有ack，
     *                      消息就一直时ack的状态。即使Consumer宕机。消息也不会丢失，甚至消费类没确认也会重新入队，重新被消费者消费。
     *          2.如何手动签收?
     *              使用Channel中的方法：
     *                  void basicAck(long deliveryTag, boolean multiple)，签收消息
     *                  void basicReject(long deliveryTag, boolean requeue)，//拒签消息，不可批量拒绝
     *                  void basicNack(long deliveryTag, boolean multiple, boolean requeue)//拒签消息，可批量拒绝
     *
     */
    @PostConstruct//为了让方法生效，我们在此配置类创建后执行方法
    public void initRabbitTemplate(){
        //设置确认回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *  1.只要消息抵达broker，回调ack=true
             *
             * 发送消息时指定消息唯一id：
             * public void convertAndSend(String exchange, String routingKey, final Object object,
             *                              @Nullable CorrelationData correlationData)
             * @param correlationData   当前消息的唯一关联数据（成员变量id：消息的唯一id）
             *                          该参数由消息发送时指定，发送时不指定为空。发送时指定，回调时就知道那个消息被接收到了。
             * @param ack       消息是否成功收到
             * @param cause     消息失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("确认回调...：correlationData["+correlationData+"]===>ack["+ack+"]===>cause["+cause+"]");
            }
        });
        //设置消息未正确抵达队列的回调
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 触发时机：消息未正确抵达队列
             * @param message       投递失败的消息的详细信息
             * @param replyCode     回复的状态码
             * @param replyText     回复的文本
             * @param exchange      消息投递的交换机
             * @param routingKey    消息投递的路由键
             *
             * 测试时可以设置不存在的路由键，导致消息无法到达任何队列，会触发回调方法。
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("未投递到队列回调：message["+message+"]===>replyCode["+replyCode+"]===>replyText["
                        +replyText+"]===>exchange["+exchange+"]===>exchange["+routingKey+"]");
            }
        });
    }
}
