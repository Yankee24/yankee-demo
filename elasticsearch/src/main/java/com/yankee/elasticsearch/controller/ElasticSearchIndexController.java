package com.yankee.elasticsearch.controller;

import com.yankee.elasticsearch.service.EalsticSearchIndexService;
import com.yankee.elasticsearch.vo.CreateIndexVO;
import com.yankee.elasticsearch.vo.UpdateIndexMappingVO;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("createIndex")
    public ResponseEntity<Boolean> createIndex(@RequestBody CreateIndexVO createIndexVO) throws IOException {
        log.info("参数为：{}", createIndexVO);
        String index = createIndexVO.getIndex();
        Map<String, Object> setting = createIndexVO.getSetting();
        Map<String, Object> mapping = createIndexVO.getMapping();
        return ResponseEntity.status(HttpStatus.CREATED).body(indexService.createIndex(index, setting, mapping));
    }

    @PostMapping("updateIndexMapping")
    public ResponseEntity<Boolean> updateIndexMapping(@RequestBody UpdateIndexMappingVO updateIndexMappingVO) throws IOException {
        log.info("参数为：{}", updateIndexMappingVO);
        String index = updateIndexMappingVO.getIndex();
        Map<String, Object> mapping = updateIndexMappingVO.getMapping();
        return ResponseEntity.status(HttpStatus.CREATED).body(indexService.updateIndexMapping(index, mapping));
    }

    @PostMapping("deleteIndex")
    public ResponseEntity<Boolean> deleteIndex(@RequestParam(value = "index") String index) throws IOException {
        log.info("删除的索引为：{}", index);
        return ResponseEntity.status(HttpStatus.CREATED).body(indexService.deleteIndex(index));
    }
}
