package com.yankee.kafka;

import com.yankee.common.utils.PropertiesUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
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
        PropertiesUtils propertiesUtil = new PropertiesUtils("kafka-consumer.properties");

        // 集群配置
        Properties properties = propertiesUtil.getAllWithProperties();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // 订阅要消费的主题
        consumer.subscribe(Collections.singletonList("order"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                LOG.info("message: {}, offset: {}, partition: {}", record.value(), record.offset(), record.partition());
            }
        }
    }
}
