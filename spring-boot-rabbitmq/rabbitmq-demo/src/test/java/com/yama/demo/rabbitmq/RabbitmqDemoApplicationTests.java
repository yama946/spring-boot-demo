package com.yama.demo.rabbitmq;

import com.yama.demo.rabbitmq.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试CachingConnectionFactory、RabbitTemplate、AmqpAdmin组件的使用
 *
 * 备注：我们可以直接使用@Bean就可以注入，不必要使用AmqpAdmin。
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqDemoApplicationTests {
    /**
     * 可用于创建：交换机、队列、绑定关系
     */
    @Autowired
    private AmqpAdmin amqpAdmin;

    /**
     * 用于消息的发送
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 使用amqpadmin创建不同类型的交换机
     * public AbstractExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> arguments)
     * 参数：
     *         1. exchange:交换机名称
     *         2. type:交换机类型
     *             DIRECT("direct"),：定向，精确匹配（routing-key---->binding-key）
     *             FANOUT("fanout"),：扇形（广播），发送消息到每一个与之绑定队列，将消息给每一队列复制。
     *             TOPIC("topic"),通配符的方式
     *             HEADERS("headers");参数匹配
     *         3. durable:是否持久化,重启rabbitmq，是否还在
     *         4. autoDelete:自动删除，当交换机没有任何绑定时，是否自动删除
     *         5. arguments：参数
     */
    @Test
    public void createExchangeTest() {
        amqpAdmin.declareExchange(new DirectExchange("demo.exchange.direct",true,false));
        amqpAdmin.declareExchange(new FanoutExchange("demo.exchange.fanout",true,false));
        amqpAdmin.declareExchange(new TopicExchange("demo.exchange.topic",true,false));
    }
    /**
     * 使用AmqpAdmin创建队列
     * 参数：
     *          1. queue：队列名称
     *          2. durable:是否持久化，当mq重启之后，还在
     *          3. exclusive：
     *              * 是否独占。只能有一个消费者监听这队列
     *              * 当Connection关闭时，是否删除队列
     *          4. autoDelete:是否自动删除。当没有Consumer时，是否自动删除掉。
     *          5. arguments：参数。
     */
    @Test
    public void createQueueTest(){
        amqpAdmin.declareQueue(new Queue("demo.queue",true,false,false,null));
    }
    /**
     * 使用AmqpAdmin创建队列与交换机绑定关系，其中交换机也可以绑定交换机，实现多级路由
     * 	public Binding(String destination, DestinationType destinationType, String exchange, String routingKey,
     *                        @Nullable Map<String, Object> arguments)
     *  参数：
     *  1. destination:
     *          目的地，指定交换机和谁绑定，一般是队列名，
     *  2. destinationType：
     *          目标类型，可以是交换机和队列，一般使用队列，绑定交换机是实现多级路由的效果
     *  3. exchange：
     *          交换机的名字
     *  4. routingKey：绑定的路由键
     *  5. arguments：参数
     */
    @Test
    public void createBindingRelationTest(){
        amqpAdmin.declareBinding(new Binding("demo.queue", Binding.DestinationType.QUEUE,
                "demo.exchange.direct","demo.queue",null));
    }

    /**
     * 使用rabbitTemplate进行消息的发送等操作
     */
    @Test
    public void sendMessageTest(){
        Person person = new Person("yama", 23, 173.43d, 65.23d, "河南郑州金水区");
        rabbitTemplate.convertAndSend("demo.exchange.direct","demo.queue",person);
    }

}
