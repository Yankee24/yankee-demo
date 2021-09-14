package com.yankee.elasticsearch.service;

import com.alibaba.fastjson.JSON;
import com.yankee.elasticsearch.document.Document;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/13
 */
@Slf4j
@Service
public class ElasticSearchSearchService<T extends Document> {
    @Resource
    private RestHighLevelClient client;

    /**
     * 获取所有的document列表
     *
     * @param index 索引
     * @param clazz 类型
     * @return list
     * @throws IOException
     */
    public List<T> searchAllDocument(String index, Class<T> clazz) throws IOException {
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        request.source(searchSourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        return getSearchResult(response, clazz);
    }

    /**
     * 根据某个字段准确查找命中结果
     *
     * @param index 索引
     * @param key   关键列
     * @param value 关键列的值
     * @param clazz 类型
     * @return list
     * @throws IOException
     */
    public List<T> searchDocumentByKey(String index, String key, Object value, Class<T> clazz) throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(key, value);
        searchSourceBuilder.query(termQueryBuilder);
        request.source(searchSourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        return getSearchResult(response, clazz);
    }

    /**
     * 获取查询结果
     *
     * @param response response
     * @param clazz    类型
     * @return list
     */
    private List<T> getSearchResult(SearchResponse response, Class<T> clazz) {
        SearchHit[] hits = response.getHits().getHits();
        List<T> documents = new ArrayList<>();
        for (SearchHit hit : hits) {
            documents.add(JSON.parseObject(hit.getSourceAsString(), clazz));
        }
        return documents;
    }
}
