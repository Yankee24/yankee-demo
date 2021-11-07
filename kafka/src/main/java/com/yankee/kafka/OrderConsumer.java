package com.yankee.kafka;

import com.yankee.common.util.PropertiesUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

/**
 * @author Yankee
 * @date 2021/11/7 22:24
 */
public class OrderConsumer {
    public static void main(String[] args) {
        Logger LOG = LoggerFactory.getLogger(OrderConsumer.class);

        // 读取配置文件
        PropertiesUtil propertiesUtil = new PropertiesUtil("kafka-consumer.properties");

        // 集群配置
        Properties properties = propertiesUtil.getAllWithProperties();
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);

        // 订阅要消费的主题
        kafkaConsumer.subscribe(Collections.singletonList("order"));
        while (true) {
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                LOG.info("要消费的数据为：{}", consumerRecord.value());
            }
        }
    }
}
