package com.yankee.elasticsearch;

import com.yankee.elasticsearch.service.EalsticSearchIndexService;
import com.yankee.elasticsearch.utils.JsonUtil;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/10
 */
public class ElasticSearchIndexTest extends ElasticSearchApplicationTests {
    @Resource
    private EalsticSearchIndexService indexService;

    @Test
    public void testCreateIndex() throws IOException {
        Map<String, Object> setting = new HashMap<>();
        setting.put("number_of_shards", 3);
        setting.put("number_of_replicas", 2);
        String map = "{\n" +
                "  \"properties\": {\n" +
                "    \"city\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"sex\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"name\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"id\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"age\": {\n" +
                "      \"type\": \"integer\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Map<String, Object> mapping = JsonUtil.parseJsonToMap(map);
        Boolean flag = indexService.createIndex("test22", setting, mapping);
        System.out.println(flag);
    }

    @Test
    public void testUpdateIndexMapping() throws IOException {
        String map = "{\n" +
                "  \"properties\": {\n" +
                "    \"city\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"sex\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"name\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"id\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"age\": {\n" +
                "      \"type\": \"integer\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Map<String, Object> mapping = JsonUtil.parseJsonToMap(map);
        System.out.println(indexService.updateIndexMapping("user", mapping));
    }

    @Test
    public void testDeleteIndex() throws IOException {
        Boolean flag = indexService.deleteIndex("user");
        System.out.println(flag);
    }

    @Test
    public void testGetIndex() throws IOException {
        GetIndexResponse response = indexService.getIndex("user");
        // 获取setting
        System.out.println(response.getSettings());
        // 获取mapping
        Map<String, MappingMetadata> mappings = response.getMappings();
        for (Map.Entry<String, MappingMetadata> stringMappingMetadataEntry : mappings.entrySet()) {
            System.out.println(stringMappingMetadataEntry.getValue().getSourceAsMap());
        }
        System.out.println(response.getAliases());
    }

    @Test
    public void testExistIndex() throws IOException {
        Boolean flag = indexService.existIndex("user");
        System.out.println(flag);
    }

    @Test
    public void testCloseIndex() throws IOException {
        Boolean flag = indexService.closeIndex("user");
        System.out.println(flag);
    }

    @Test
    public void testOpenIndex() throws IOException {
        Boolean flag = indexService.openIndex("user");
        System.out.println(flag);
    }
}
