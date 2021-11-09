package com.yankee.kafka;

import com.yankee.common.utils.PropertiesUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.UUID;

/**
 * @author Yankee
 * @version 1.0
 * @description 生产者
 * @date 2021/11/9 14:32
 */
public class OrderProducer {
    public static void main(String[] args) throws InterruptedException {
        Logger LOG = LoggerFactory.getLogger(OrderProducer.class);

        // 读取配置文件
        PropertiesUtils propertiesUtil = new PropertiesUtils("kafka-producer.properties");

        // 集群配置
        Properties properties = propertiesUtil.getAllWithProperties();
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        for (int i = 1; i <= 10000; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>("order", "订单信息：" + UUID.randomUUID());
            // 回调函数，获得分区信息
            producer.send(record, (metadata, e) -> {
                if (null != e) {
                    LOG.error("send error: " + e.getMessage());
                } else {
                    LOG.info("message: {}, offset: {}, partition: {}", record.value(), metadata.offset(), metadata.partition());
                }
            });
            // Thread.sleep(100);
        }
    }
}
