package com.yankee.elasticsearch.utils;

import com.yankee.elasticsearch.config.ElasticsearchConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/10
 */
@Slf4j
@Component
public class ElasticSearchClientUtil {
    public RestHighLevelClient restHighLevelClient;

    @Resource
    private ElasticsearchConfiguration elasticsearchConfiguration;

    /**
     * 初始化RestHighLevelClient
     */
    @PostConstruct
    private void init() {
        this.initRestHighLevelClient();
    }

    private ElasticSearchClientUtil() {
    }

    /**
     * 初始化ElasticSearch连接
     */
    private void initRestHighLevelClient() {
        try {
            if (restHighLevelClient == null) {
                log.info("***************************连接ElasticSearch开始***************************");
                restHighLevelClient = getHighLevelClient();
                log.info("***************************连接ElasticSearch结束***************************");
            }
        } catch (Exception e) {
            log.error("ElasticSearch连接出现异常：{}", e.toString());
        }
    }

    /**
     * 获取ElasticSearch节点
     *
     * @return HttpHost[]
     */
    private HttpHost[] getNodes() {
        HttpHost[] httpHosts = null;
        Boolean isSingleton = elasticsearchConfiguration.getIsSingleton();
        String node = elasticsearchConfiguration.getNode();
        String nodeSchema = elasticsearchConfiguration.getNodeSchema();
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
    private RestHighLevelClient getHighLevelClient() {
        String node = elasticsearchConfiguration.getNode();
        int connTimeout = elasticsearchConfiguration.getConnTimeout();
        int socketTimeout = elasticsearchConfiguration.getSocketTimeout();
        int connectionRequestTimeout = elasticsearchConfiguration.getConnectionRequestTimeout();
        HttpHost[] nodes = getNodes();
        log.info("连接ElasticSearch：{}", node);
        RestClientBuilder restClientBuilder = RestClient.builder(nodes).setRequestConfigCallback(builder -> builder.setConnectTimeout(connTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout));
        restHighLevelClient = new RestHighLevelClient(restClientBuilder);
        return restHighLevelClient;
    }
}
