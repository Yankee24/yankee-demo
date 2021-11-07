package com.yankee.kafka;

import com.yankee.common.util.PropertiesUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author Yankee
 * @date 2021/11/7 17:28
 */
public class OrderProducer {
    public static void main(String[] args) throws InterruptedException {
        Logger LOG = LoggerFactory.getLogger(OrderProducer.class);

        // 读取配置文件
        PropertiesUtil propertiesUtil = new PropertiesUtil("kafka-producer.properties");

        // 集群配置
        Properties properties = propertiesUtil.getAllWithProperties();
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        for (int i = 0; i < 1000; i++) {
            kafkaProducer.send(new ProducerRecord<String, String>("order", "订单信息" + i));
            Thread.sleep(100);
        }
    }
}
