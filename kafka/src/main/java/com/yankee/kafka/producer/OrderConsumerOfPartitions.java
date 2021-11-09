package com.yankee.kafka.producer;

import com.yankee.common.utils.PropertiesUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author Yankee
 * @version 1.0
 * @description 消费某个主题的某些分区
 * @date 2021/11/9 14:32
 */
public class OrderConsumerOfPartitions {
    public static void main(String[] args) {
        Logger LOG = LoggerFactory.getLogger(OrderConsumerOfPartitions.class);

        // 获取配置
        PropertiesUtils propertiesUtils = new PropertiesUtils("kafka-consumer-manually.properties");

        // 连接kafka集群
        Properties properties = propertiesUtils.getAllWithProperties();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // topic主题
        String topic = propertiesUtils.get("topic");
        // 订阅分区
        TopicPartition partition0 = new TopicPartition(topic, 0);
        TopicPartition partition1 = new TopicPartition(topic, 1);
        consumer.assign(Arrays.asList(partition0, partition1));

        // 指定分区消费
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                LOG.info("message: {}, offset: {}, partition: {}", record.value(), record.offset(), record.partition());
                consumer.commitAsync();
            }
        }
    }
}
