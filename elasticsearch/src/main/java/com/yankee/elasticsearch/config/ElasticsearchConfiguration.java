package com.yankee.elasticsearch.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Yankee
 * @program yankee-demo
 * @description ElasticSearch配置
 * @since 2021/9/10
 */
@Data
@Slf4j
@Configuration
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

    /**
     * 获取ElasticSearch节点
     *
     * @return HttpHost[]
     */
    private HttpHost[] getNodes() {
        HttpHost[] httpHosts = null;
        if (!isSingleton) {
            String[] hosts = node.split(",");
            httpHosts = new HttpHost[hosts.length];
            for (int i = 0; i < hosts.length; i++) {
                String host = hosts[i].split(":")[0];
                int port = Integer.parseInt(hosts[i].split(":")[1]);
                httpHosts[i] = new HttpHost(host, port, nodeSchema);
            }
        } else {
            String[] hosts = node.split(":");
            httpHosts = new HttpHost[1];
            httpHosts[0] = new HttpHost(hosts[0], Integer.parseInt(hosts[1]), nodeSchema);
        }
        return httpHosts;
    }

    /**
     * 获取ElasticSearch连接
     *
     * @return RestHighLevelClient
     */
    @Bean(destroyMethod = "close", name = "client")
    public RestHighLevelClient initRestHighLevelClient() {
        HttpHost[] nodes = getNodes();
        log.info("连接ElasticSearch：{}", node);
        RestClientBuilder restClientBuilder = RestClient.builder(nodes).setRequestConfigCallback(builder -> builder.setConnectTimeout(connTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout));
        return new RestHighLevelClient(restClientBuilder);
    }
}
