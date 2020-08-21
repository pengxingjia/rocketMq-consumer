package im.qingtui.rocketmq.consumer.dynamic.consumer.rocket.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

@Data
@TableName(value = "rocket_info")
public class RocketInfo implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @TableField(value = "gmt_create")
    private Long gmtCreate;

    @TableField(value = "gmt_modify")
    private Long gmtModify;

    /**
     * rocketmq的ip地址
     */
    @TableField(value = "addr_ip")
    private String addrIp;

    /**
     * 消息队列说明
     */
    @TableField(value = "comments")
    private String comments;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFY = "gmt_modify";

    public static final String COL_ADDR_IP = "addr_ip";

    public static final String COL_COMMENTS = "comments";
}