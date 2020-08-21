package im.qingtui.rocketmq.consumer.dynamic.consumer.rocket.controller;

import im.qingtui.rocketmq.consumer.dynamic.consumer.rocket.service.ConsumerPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消费者动态创建测试类
 *
 * @author qiaofeng
 */
@RestController
public class ConsumerControllerTest {

    @Autowired
    ConsumerPlatformService consumerPlatformService;

    /**
     * 启动一个消费者
     * @param rocketTopicId 消费者信息记录id
     */
    @RequestMapping(value = "/consumer/start/{rocketTopicId}")
    public String startConsumer(@PathVariable(value = "rocketTopicId") String rocketTopicId){
        consumerPlatformService.startConsumer(rocketTopicId);
        return "ok";
    }

    /**
     * 下线一个消费者
     * @param rocketTopicId 消费者信息记录id
     */
    @RequestMapping(value = "/consumer/remove/{rocketTopicId}")
    public String removeConsumer(@PathVariable(value = "rocketTopicId") String rocketTopicId){
        consumerPlatformService.removeConsumer(rocketTopicId);
        return "ok";
    }

    @RequestMapping(value = "/consumer/restart/{rocketTopicId}")
    public String restartConsumer(@PathVariable(value = "rocketTopicId") String rocketTopicId){
        consumerPlatformService.restartConsumer(rocketTopicId);
        return "ok";
    }

}