package com.yankee.kafka.consumer;

import com.yankee.common.utils.DBConnectionPool;
import com.yankee.common.utils.PropertiesUtils;
import com.yankee.kafka.bean.KafkaOffset;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.*;

/**
 * @author Yankee
 * @version 1.0
 * @description 手动维护kafka的offset并提交到mysql
 * @date 2021/11/9 14:32
 */
public class OrderConsumerOfMySQL {
    public static void main(String[] args) {
        Logger LOG = LoggerFactory.getLogger(OrderConsumerOfMySQL.class);

        // 读取配置文件
        PropertiesUtils propertiesUtil = new PropertiesUtils("kafka-consumer-manually.properties");

        // 连接kafka
        Properties properties = new Properties();
        properties.put("bootstrap.servers", propertiesUtil.get("bootstrap.servers"));
        properties.put("key.deserializer", propertiesUtil.get("key.deserializer"));
        properties.put("value.deserializer", propertiesUtil.get("value.deserializer"));
        String topic = propertiesUtil.get("topic");
        properties.put("topic", topic);
        String groupid = propertiesUtil.get("group.id");
        properties.put("group.id", groupid);
        properties.put("auto.offset.reset", propertiesUtil.get("auto.offset.reset"));
        properties.put("enable.auto.commit", propertiesUtil.get("enable.auto.commit"));
        properties.put("max.poll.interval.ms", propertiesUtil.get("max.poll.interval.ms"));
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // 订阅主题topic
        consumer.subscribe(Collections.singletonList(topic));

        Set<TopicPartition> assignment = new HashSet<>();
        while (assignment.size() == 0) {
            consumer.poll(Duration.ofMillis(100));
            // assignment用来获取消费者所分配道德分区消息的
            assignment = consumer.assignment();
        }

        // 获取JDBC连接，并查询topic的offset
        Connection connection = null;
        try {
            DBConnectionPool instance = DBConnectionPool.getInstance();
            connection = instance.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 存储partitions和offset信息
        Map<Integer, Long> map = new HashMap<>();

        // 从某个分区的offset开始消费
        for (TopicPartition topicPartition : assignment) {
            try {
                String sql = "SELECT * from kafka_offset WHERE topic = ? AND groupid = ? AND partitions = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, "order");
                statement.setString(2, groupid);
                int partition = topicPartition.partition();
                statement.setInt(3, partition);
                // 查询结果
                ResultSet resultSet = statement.executeQuery();
                KafkaOffset offset = null;
                while (resultSet.next()) {
                    offset = new KafkaOffset();
                    offset.setTopic(topic);
                    offset.setGroupid(groupid);
                    offset.setPartitions(partition);
                    offset.setFromoffset(resultSet.getLong(4));
                    offset.setUntiloffset(resultSet.getLong(5));
                }
                if (offset == null) {
                    LOG.info("topic: {}, partitions: {}-从头开始消费！", topic, topicPartition);
                    consumer.seek(topicPartition, 0);
                } else {
                    Long untiloffset = offset.getUntiloffset();
                    LOG.info("topic: {}, partitions: {}-从{}开始消费！", topic, topicPartition, untiloffset);
                    consumer.seek(topicPartition, untiloffset);
                    map.put(topicPartition.partition(), untiloffset);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        // 消费数据
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                LOG.info("message: {}, offset: {}, partition: {}", record.value(), record.offset(), record.partition());
            }
            // 异步提交offset，并报错到mysql
            Connection finalConnection = connection;
            consumer.commitAsync((offsets, exception) -> {
                for (Map.Entry<TopicPartition, OffsetAndMetadata> entry : offsets.entrySet()) {
                    try {
                        String sql = "UPDATE kafka_offset SET fromoffset = ?, untiloffset = ? WHERE topic = ? AND groupid = ? AND partitions = ?;";
                        PreparedStatement statement = finalConnection.prepareStatement(sql);
                        statement.setLong(1, map.get(entry.getKey().partition()));
                        statement.setLong(2, entry.getValue().offset());
                        statement.setString(3, topic);
                        statement.setString(4, groupid);
                        statement.setInt(5, entry.getKey().partition());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
