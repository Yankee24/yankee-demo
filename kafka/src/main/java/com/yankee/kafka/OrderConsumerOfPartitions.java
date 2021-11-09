package com.yankee.kafka;

import com.yankee.common.utils.PropertiesUtils;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        PropertiesUtils propertiesUtils = new PropertiesUtils("");

        // 连接kafka集群
        Properties properties = propertiesUtils.getAllWithProperties();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
    }
}
