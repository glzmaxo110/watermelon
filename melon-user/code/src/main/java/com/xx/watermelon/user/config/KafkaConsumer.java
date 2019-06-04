package com.xx.watermelon.user.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @description: Kafka消费监听
 * @author: xiesx
 * @createTime: 2019-05-28 15:20
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "nginxlog")
    public void listen(ConsumerRecord<?, ?> record) throws Exception {
        System.out.printf("topic = %s, offset = %d, partition = %s ,value = %s \n", record.topic(), record.offset(), record.partition(), record.value());
    }

}