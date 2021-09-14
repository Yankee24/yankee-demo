package com.yankee.elasticsearch.service;

import com.alibaba.fastjson.JSON;
import com.yankee.elasticsearch.document.Document;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/13
 */
@Slf4j
@Service
public class ElasticSearchDocumentService<T extends Document> {
    @Resource
    private RestHighLevelClient client;

    /**
     * 创建document
     *
     * @param document row
     * @return Boolean
     * @throws IOException
     */
    public Boolean createDocument(String index, T document) throws IOException {
        UUID uuid = UUID.randomUUID();
        document.setId(uuid.toString());
        IndexRequest request = new IndexRequest(index)
                .id(document.getId())
                .source(JSON.toJSONString(document), XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        return response.status().equals(RestStatus.CREATED);
    }

    /**
     * 批量插入document
     *
     * @param index     索引
     * @param documents 文档列表
     * @return Boolean
     * @throws IOException
     */
    public Boolean bulkCreateDocument(String index, List<T> documents) throws IOException {
        BulkRequest request = new BulkRequest();
        for (T document : documents) {
            String id = UUID.randomUUID().toString();
            document.setId(id);
            IndexRequest indexRequest = new IndexRequest(index)
                    .id(id)
                    .source(JSON.toJSONString(document), XContentType.JSON);
            request.add(indexRequest);
        }
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        return response.status().equals(RestStatus.CREATED);
    }

    /**
     * 获取document
     *
     * @param index 索引
     * @param clazz 类型
     * @param id    id
     * @return T
     * @throws IOException
     */
    public T infoDocument(String index, Class<T> clazz, String id) throws IOException {
        GetRequest request = new GetRequest(index, id);
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        T result = null;
        if (response.isExists()) {
            String sourceAsString = response.getSourceAsString();
            result = JSON.parseObject(sourceAsString, clazz);
        } else {
            log.error("没有找到该id的文档，id为：{}", id);
        }
        return result;
    }

    /**
     * 删除document
     *
     * @param index 索引
     * @param id    id
     * @return String
     * @throws IOException
     */
    public String deleteDocument(String index, String id) throws IOException {
        DeleteRequest request = new DeleteRequest(index, id);
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        return response.getResult().name();
    }

    /**
     * 更新document
     *
     * @param index    索引
     * @param id       id
     * @param document 文档
     * @return String
     * @throws IOException
     */
    public String updateDocument(String index, String id, Map<String, Object> document) throws IOException {
        UpdateRequest request = new UpdateRequest(index, id)
                .doc(document);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        return response.getResult().name();
    }
}
