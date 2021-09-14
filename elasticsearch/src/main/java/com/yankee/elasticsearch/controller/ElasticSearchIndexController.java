package com.yankee.elasticsearch.controller;

import com.yankee.elasticsearch.service.EalsticSearchIndexService;
import com.yankee.elasticsearch.vo.CreateIndexVO;
import com.yankee.elasticsearch.vo.UpdateIndexMappingVO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/10
 */
@RestController
@RequestMapping("/elasticsearch/index")
@Slf4j
public class ElasticSearchIndexController {
    @Resource
    private EalsticSearchIndexService indexService;

    @PostMapping("create")
    public ResponseEntity<Boolean> createIndex(@RequestBody CreateIndexVO createIndexVO) throws IOException {
        log.info("参数为：{}", createIndexVO);
        String index = createIndexVO.getIndex();
        Map<String, Object> setting = createIndexVO.getSetting();
        Map<String, Object> mapping = createIndexVO.getMapping();
        return ResponseEntity.status(HttpStatus.CREATED).body(indexService.createIndex(index, setting, mapping));
    }

    @PostMapping("updateMapping")
    public ResponseEntity<Boolean> updateIndexMapping(@RequestBody UpdateIndexMappingVO updateIndexMappingVO) throws IOException {
        log.info("参数为：{}", updateIndexMappingVO);
        String index = updateIndexMappingVO.getIndex();
        Map<String, Object> mapping = updateIndexMappingVO.getMapping();
        return ResponseEntity.status(HttpStatus.CREATED).body(indexService.updateIndexMapping(index, mapping));
    }

    @PostMapping("delete")
    public ResponseEntity<Boolean> deleteIndex(@RequestParam(value = "index") String index) throws IOException {
        log.info("删除的索引为：{}", index);
        return ResponseEntity.status(HttpStatus.CREATED).body(indexService.deleteIndex(index));
    }

    @PostMapping("get")
    public Map<String, MappingMetadata> getIndex(@RequestParam(value = "index") String index) throws IOException {
        log.info("索引信息：{}", index);
        GetIndexResponse response = indexService.getIndex(index);
        return response.getMappings();
    }

    @PostMapping("exist")
    public ResponseEntity<Boolean> existIndex(@RequestParam(value = "index") String index) throws IOException {
        log.info("判断索引是否存在：{}", index);
        return ResponseEntity.status(HttpStatus.CREATED).body(indexService.existIndex(index));
    }

    @PostMapping("close")
    public ResponseEntity<Boolean> closeIndex(@RequestParam(value = "index") String index) throws IOException {
        log.info("关闭索引：{}", index);
        return ResponseEntity.status(HttpStatus.CREATED).body(indexService.closeIndex(index));
    }

    @PostMapping("open")
    public ResponseEntity<Boolean> openIndex(@RequestParam(value = "index") String index) throws IOException {
        log.info("开启索引：{}", index);
        return ResponseEntity.status(HttpStatus.CREATED).body(indexService.openIndex(index));
    }
}
