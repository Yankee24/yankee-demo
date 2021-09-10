package com.yankee.elasticsearch.service;

import com.yankee.elasticsearch.utils.ElasticSearchClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/10
 */
@Slf4j
@Service
public class EalsticSearchIndexService {
    @Resource
    private ElasticSearchClientUtil client;

    /**
     * 判断某个index是否存在
     *
     * @param indexName 索引名称
     * @return Boolean
     * @throws IOException
     */
    public Boolean existIndex(String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);
        RestHighLevelClient restHighLevelClient = client.restHighLevelClient;
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }
}
