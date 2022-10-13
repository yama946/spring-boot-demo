package com.yama.demo.rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * springboot整合rabbitma步骤：
 *      1.导入rabbitmq的场景启动器starter：spring-boot-starter-amqp
 *      2.在配置文件使用prefix = "spring.rabbitmq"前缀，配置rabbitmq服务器连接等配置。
 *
 *      接下来就可以使用rabbitmq了。
 *其他配置注意事项：
 *      3.springboot中的自动配置类：RabbitAutoConfiguration自动生效，向容器中导入组件
 *          CachingConnectionFactory、RabbitTemplate、AmqpAdmin
 *      4.@EnableRabbit:该注解能够让@RabbitListener注解生效，可以在任何标注@Configuration的类上标注此注解
 */
//@EnableRabbit
@SpringBootApplication
public class RabbitmqDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqDemoApplication.class, args);
    }

}
