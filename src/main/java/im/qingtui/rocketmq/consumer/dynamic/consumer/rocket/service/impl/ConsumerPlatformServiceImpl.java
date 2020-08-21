package im.qingtui.rocketmq.consumer.dynamic.consumer.rocket.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import im.qingtui.rocketmq.consumer.dynamic.consumer.rocket.config.ConsumerConfig;
import im.qingtui.rocketmq.consumer.dynamic.consumer.rocket.dao.RocketInfoMapper;
import im.qingtui.rocketmq.consumer.dynamic.consumer.rocket.dao.RocketTopicMsgModelMapper;
import im.qingtui.rocketmq.consumer.dynamic.consumer.rocket.model.entity.RocketInfo;
import im.qingtui.rocketmq.consumer.dynamic.consumer.rocket.model.entity.RocketTopicMsgModel;
import im.qingtui.rocketmq.consumer.dynamic.consumer.rocket.service.ConsumerPlatformService;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消费者动态处理类
 *
 * @author qiaofeng
 */
@Service
@Slf4j
public class ConsumerPlatformServiceImpl implements ConsumerPlatformService {

    @Autowired
    RocketInfoMapper rocketInfoMapper;

    @Autowired
    RocketTopicMsgModelMapper rocketTopicMsgModelMapper;

    @Override
    public void startConsumer(String rocketTopicId) {
        if (ConsumerConfig.consumerMap.containsKey(rocketTopicId)){
            return;
        }
        RocketTopicMsgModel rocketTopicMsgModel = getRocketTopicMsgModel(rocketTopicId);
        RocketInfo rocketInfo = rocketInfoMapper.selectOne(new QueryWrapper<RocketInfo>().eq("id", rocketTopicMsgModel.getRocketId()));
        try {
            DefaultMQPushConsumer mQPushConsumer = addConsumerIntoMap(rocketInfo.getAddrIp(), rocketTopicMsgModel);
            ConsumerConfig.consumerMap.put(rocketTopicId, mQPushConsumer);
        } catch (MQClientException e) {
            log.error("创建消费者失败，原因：{}", e.getErrorMessage());
        }
    }

    @Override
    public void removeConsumer(String rocketTopicId) {
        if (ConsumerConfig.consumerMap.containsKey(rocketTopicId)) {
            ConsumerConfig.consumerMap.get(rocketTopicId).shutdown();
            ConsumerConfig.consumerMap.remove(rocketTopicId);
        }
    }

    @Override
    public void restartConsumer(String rocketTopicId) {
        if (!ConsumerConfig.consumerMap.containsKey(rocketTopicId)){
            return;
        }
        DefaultMQPushConsumer mqPushConsumer = ConsumerConfig.consumerMap.get(rocketTopicId);
        mqPushConsumer.shutdown();

        ConsumerConfig.consumerMap.remove(rocketTopicId);
        startConsumer(rocketTopicId);
    }

    private RocketTopicMsgModel getRocketTopicMsgModel(String rocketTopicId){
        RocketTopicMsgModel rocketTopicMsgModel = rocketTopicMsgModelMapper.selectOne(new QueryWrapper<RocketTopicMsgModel>().eq("id", rocketTopicId));
        if (rocketTopicMsgModel == null){
            throw new RuntimeException("该消费者id不存在" + rocketTopicId);
        }
        return rocketTopicMsgModel;
    }

    /**
     * 创建对应的消费者服务
     * @param addrIp 消息队列服务ip:端口
     * @param rocketTopicMsgModel 消费者需要消费的队列
     */
    private DefaultMQPushConsumer addConsumerIntoMap(String addrIp, RocketTopicMsgModel rocketTopicMsgModel) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rocketTopicMsgModel.getConsumerGroup());
        consumer.setNamesrvAddr(addrIp);
        consumer.subscribe(rocketTopicMsgModel.getTopicName(), rocketTopicMsgModel.getSubExpression());
        consumer.setMessageModel(rocketTopicMsgModel.getMsgModelType() == 1 ? MessageModel.CLUSTERING : MessageModel.BROADCASTING);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                try {
                    if (msgs.size() > 0){
                        MessageExt messageExt = msgs.get(0);
                        consumerMsg(rocketTopicMsgModel.getMsgModel(), new String(messageExt.getBody(), StandardCharsets.UTF_8));
                    }
                }catch (Throwable e){
                    log.error("发生异常:{}", e.getMessage());
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();
        return consumer;
    }

    //消费消息
    private void consumerMsg(String msgModel, String msg){
        log.info("收到的msg:{}", msg);
        log.info("模版的msgModel:{}", msgModel);
        JSONObject msgModelJson = JSONObject.parseObject(msgModel);
        Iterator<Entry<String, Object>> iterator = msgModelJson.entrySet().iterator();
        JSONObject msgJson = JSONObject.parseObject(msg);

        while (iterator.hasNext()){
            Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            if (msgJson.containsKey(key)){
                next.setValue(msgJson.get(key));
            }
        }
        log.info("收到的msg:{}", msg);
        log.info("处理后模版的msgModel:{}", msgModelJson.toJSONString());
    }
}