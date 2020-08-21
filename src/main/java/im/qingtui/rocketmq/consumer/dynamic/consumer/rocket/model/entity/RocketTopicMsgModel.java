package im.qingtui.rocketmq.consumer.dynamic.consumer.rocket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

@Data
@TableName(value = "rocket_topic_msg_model")
public class RocketTopicMsgModel implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @TableField(value = "gmt_create")
    private Long gmtCreate;

    @TableField(value = "gmt_modify")
    private Long gmtModify;

    /**
     * 主题名
     */
    @TableField(value = "topic_name")
    private String topicName;

    /**
     * 消费者组
     */
    @TableField(value = "consumer_group")
    private String consumerGroup;

    /**
     * tag,过个tag时，用|隔开
     */
    @TableField(value = "sub_expression")
    private String subExpression;

    /**
     * 解析消息的模板，只支持一维健值对
     */
    @TableField(value = "msg_model")
    private String msgModel;

    /**
     * 消息队列信息id
     */
    @TableField(value = "rocket_id")
    private String rocketId;

    /**
     * 1集群模式，2广播模式
     */
    @TableField(value = "msg_model_type")
    private Integer msgModelType;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFY = "gmt_modify";

    public static final String COL_TOPIC_NAME = "topic_name";

    public static final String COL_CONSUMER_GROUP = "consumer_group";

    public static final String COL_SUB_EXPRESSION = "sub_expression";

    public static final String COL_MSG_MODEL = "msg_model";

    public static final String COL_ROCKET_ID = "rocket_id";

    public static final String COL_MSG_MODEL_TYPE = "msg_model_type";
}