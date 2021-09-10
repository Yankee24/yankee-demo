package com.yankee.elasticsearch.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Yankee
 * @program yankee-demo
 * @description ElasticSearch配置
 * @since 2021/9/10
 */
@Data
@Component
@ConfigurationProperties(prefix = "elasticsearch")
@PropertySource(value = {"classpath:config/elasticsearch.yml"}, encoding = "UTF-8")
public class ElasticsearchConfiguration {
    @Value("${isSingleton}")
    private Boolean isSingleton;

    @Value("${node}")
    private String node;

    @Value("${nodeSchema}")
    private String nodeSchema;

    @Value("${connTimeout}")
    private int connTimeout;

    @Value("${socketTimeout}")
    private int socketTimeout;

    @Value("${connectionRequestTimeout}")
    private int connectionRequestTimeout;
}
