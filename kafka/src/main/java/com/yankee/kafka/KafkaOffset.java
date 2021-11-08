package com.yankee.kafka;

/**
 * @author Yankee
 * @date 2021/11/8 21:59
 */
public class KafkaOffset {
    /**
     * topic主题
     */
    private String topic;

    /**
     * 消费者组
     */
    private String groupid;

    /**
     * 分区
     */
    private Integer partitions;

    /**
     * 开始offset
     */
    private Long fromoffset;

    /**
     * 结尾offset
     */
    private Long untiloffset;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public Integer getPartitions() {
        return partitions;
    }

    public void setPartitions(Integer partitions) {
        this.partitions = partitions;
    }

    public Long getFromoffset() {
        return fromoffset;
    }

    public void setFromoffset(Long fromoffset) {
        this.fromoffset = fromoffset;
    }

    public Long getUntiloffset() {
        return untiloffset;
    }

    public void setUntiloffset(Long untiloffset) {
        this.untiloffset = untiloffset;
    }

    @Override
    public String toString() {
        return "KafkaOffset{" +
                "topic='" + topic + '\'' +
                ", groupid='" + groupid + '\'' +
                ", partitions=" + partitions +
                ", fromoffset=" + fromoffset +
                ", untiloffset=" + untiloffset +
                '}';
    }
}
