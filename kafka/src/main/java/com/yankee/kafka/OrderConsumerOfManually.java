package com.yankee.kafka;

import com.yankee.common.utils.PropertiesUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

public class OrderConsumerOfManually {
    public static void main(String[] args) {
        Logger LOG = LoggerFactory.getLogger(OrderConsumerOfManually.class);

        // 读取配置文件
        PropertiesUtils propertiesUtil = new PropertiesUtils("kafka-consumer-manually.properties");

        // 连接kafka
        Properties properties = propertiesUtil.getAllWithProperties();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // 订阅消费的主题
        consumer.subscribe(Collections.singletonList("order"));
        final int minBatchSize = 100;
        ArrayList<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    buffer.add(record);
                    LOG.info("message: {}, offset: {}, partition: {}", record.value(), record.offset(), record.partition());
                }
                // 手动提交offset（异步提交）
                if (buffer.size() >= minBatchSize) {
                    consumer.commitAsync();
                    buffer.clear();
                }
            }
        } finally {
            // 最后一次同步阻塞式提交
            try {
                consumer.commitSync();
            } finally {
                consumer.close();
            }
        }
    }
}
