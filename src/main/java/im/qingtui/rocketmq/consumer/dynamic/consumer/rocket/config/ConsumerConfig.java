package im.qingtui.rocketmq.consumer.dynamic.consumer.rocket.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

/**
 * 消费者相关配置
 *
 * @author qiaofeng
 */
public class ConsumerConfig {

    /**
     * 存储消费者对象，可以扩展为redis
     */
    public static Map<String, DefaultMQPushConsumer> consumerMap = new ConcurrentHashMap<>();
}