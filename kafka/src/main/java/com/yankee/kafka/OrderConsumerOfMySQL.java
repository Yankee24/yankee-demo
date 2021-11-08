package com.yankee.kafka;

import com.yankee.common.utils.DBConnectionPool;
import com.yankee.common.utils.PropertiesUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.*;

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
        String topic = propertiesUtil.get("topic");
        properties.put("topic", topic);
        String groupid = propertiesUtil.get("group.id");
        properties.put("group.id", groupid);
        properties.put("auto.offset.reset", propertiesUtil.get("auto.offset.reset"));
        properties.put("enable.auto.commit", propertiesUtil.get("enable.auto.commit"));
        properties.put("max.poll.interval.ms", propertiesUtil.get("max.poll.interval.ms"));
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // 获取分区信息
        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);

        // 获取JDBC连接，并查询topic的offset
        Connection connection = null;
        // 存储offset的对象
        List<KafkaOffset> offsets = new ArrayList<>();
        try {
            DBConnectionPool instance = DBConnectionPool.getInstance();
            connection = instance.getConnection();
            for (PartitionInfo partitionInfo : partitionInfos) {
                consumer.subscribe(Collections.singletonList("topic"));
                KafkaOffset offset = new KafkaOffset();
                String sql = "SELECT * from kafka_offset WHERE topic = ? AND groupid = ? AND partitions = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, "order");
                statement.setString(2, groupid);
                int partition = partitionInfo.partition();
                statement.setInt(3, partition);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    long fromoffset = resultSet.getLong(4);
                    long untiloffset = resultSet.getLong(5);
                    offset.setTopic(topic);
                    offset.setGroupid(groupid);
                    offset.setPartitions(partition);
                    offset.setFromoffset(fromoffset);
                    offset.setUntiloffset(untiloffset);
                }
                offsets.add(offset);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Set<TopicPartition> assignment = new HashSet<>();
        while (assignment.size() == 0) {
            consumer.poll(Duration.ofMillis(100));
            // assignment用来获取消费者所分配道德分区消息的
            assignment = consumer.assignment();
        }
        System.out.println(assignment);

        for (TopicPartition topicPartition : assignment) {
            long offset = 80;
            consumer.seek(topicPartition, offset);
        }

        // 从某个分区的offset开始消费
        if (offsets.isEmpty()) {

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
