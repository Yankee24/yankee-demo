package com.yankee.kafka;

import com.yankee.common.utils.DBConnectionPool;
import com.yankee.common.utils.PropertiesUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class OrderConsumerOfMySQL {
    public static void main(String[] args) {
        Logger LOG = LoggerFactory.getLogger(OrderConsumerOfMySQL.class);

        // 读取配置文件
        PropertiesUtils propertiesUtil = new PropertiesUtils("kafka-consumer-mysql.properties");

        // 连接kafka
        Properties properties = new Properties();
        properties.put("bootstrap.servers", propertiesUtil.get("bootstrap.servers"));
        properties.put("key.deserializer", propertiesUtil.get("key.deserializer"));
        properties.put("value.deserializer", propertiesUtil.get("value.deserializer"));
        properties.put("topic", propertiesUtil.get("topic"));
        String groupid = propertiesUtil.get("group.id");
        properties.put("group.id", groupid);
        properties.put("auto.offset.reset", propertiesUtil.get("auto.offset.reset"));
        properties.put("enable.auto.commit", propertiesUtil.get("enable.auto.commit"));
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // 订阅消费的主题
        consumer.subscribe(Collections.singletonList("topic"));

        // 获取分区
        Set<TopicPartition> assignment = new HashSet<>();
        while (assignment.size() == 0) {
            consumer.poll(Duration.ofMillis(100));
            assignment = consumer.assignment();
        }
        // 获取topic的分区
        for (TopicPartition topicPartition : assignment) {

        }

        // 获取JDBC连接，并查询topic的offset
        Connection connection = null;
        try {
            DBConnectionPool instance = DBConnectionPool.getInstance();
            connection = instance.getConnection();
            String sql = "SELECT * from kafka_offset WHERE topic = ? AND groupid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "order");
            statement.setString(2, groupid);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // 消费数据
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                LOG.info("message: {}, offset: {}, partition: {}", record.value(), record.offset(), record.partition());
            }
            // 异步提交offset，并报错到mysql
            consumer.commitAsync();
        }
    }
}
