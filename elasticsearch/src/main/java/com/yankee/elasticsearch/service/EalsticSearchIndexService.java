package com.yankee.elasticsearch.service;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

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
    private RestHighLevelClient client;

    /**
     * 创建index
     *
     * @param index 索引名称
     * @return Boolean
     * @throws IOException
     */
    public Boolean createIndex(String index, Map<String, ?> setting, Map<String, ?> mapping) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(index);
        request.settings(setting)
                .mapping(mapping);
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 更新索引的mapping
     *
     * @param index   索引
     * @param mapping mapping
     * @return Boolean
     * @throws IOException
     */
    public Boolean updateIndexMapping(String index, Map<String, Object> mapping) throws IOException {
        PutMappingRequest request = new PutMappingRequest(index);
        request.source(mapping);
        AcknowledgedResponse response = client.indices().putMapping(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 删除index
     *
     * @param index 索引名称
     * @return Boolean
     * @throws IOException
     */
    public Boolean deleteIndex(String index) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 获取某个index的信息
     *
     * @param index 索引名称
     * @return GetIndexResponse
     * @throws IOException
     */
    public GetIndexResponse getIndex(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        return client.indices().get(request, RequestOptions.DEFAULT);
    }

    /**
     * 判断某个index是否存在
     *
     * @param index 索引名称
     * @return Boolean
     * @throws IOException
     */
    public Boolean existIndex(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 关闭index
     *
     * @param index 索引名称
     * @return Boolean
     * @throws IOException
     */
    public Boolean closeIndex(String index) throws IOException {
        CloseIndexRequest request = new CloseIndexRequest(index);
        CloseIndexResponse response = client.indices().close(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 开启index
     *
     * @param index 索引名称
     * @return Boolean
     * @throws IOException
     */
    public Boolean openIndex(String index) throws IOException {
        OpenIndexRequest request = new OpenIndexRequest(index);
        OpenIndexResponse response = client.indices().open(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }
}
