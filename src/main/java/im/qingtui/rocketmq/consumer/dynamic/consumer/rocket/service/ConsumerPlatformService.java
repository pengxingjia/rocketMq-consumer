package im.qingtui.rocketmq.consumer.dynamic.consumer.rocket.service;

/**
 * 消费者服务接口
 *
 * @author qiaofeng
 */
public interface ConsumerPlatformService {

    /**
     * 启动一个消费者
     * @param rocketTopicId 消费者信息记录id
     */
    void startConsumer(String rocketTopicId);

    /**
     * 下线一个消费者
     * @param rocketTopicId 消费者信息记录id
     */
    void removeConsumer(String rocketTopicId);

    /**
     * 重启消费者
     * @param rocketTopicId 消费者信息记录id
     */
    void restartConsumer(String rocketTopicId);
}