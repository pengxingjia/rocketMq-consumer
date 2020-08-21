package im.qingtui.rocketmq.consumer.dynamic.consumer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "im.qingtui.rocketmq.consumer.dynamic.consumer.rocket.dao")
public class DynamicConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicConsumerApplication.class, args);
	}

}
