package com.yankee.elasticsearch.controller;

import com.yankee.elasticsearch.document.UserDocument;
import com.yankee.elasticsearch.service.ElasticSearchSearchService;
import com.yankee.elasticsearch.vo.SearchDocumentByKeyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/13
 */
@RestController
@RequestMapping("/elasticsearch/user")
@Slf4j
public class ElasticSearchSearchController {
    @Resource
    private ElasticSearchSearchService<UserDocument> searchService;

    @GetMapping("searchAllDocuments")
    public List<UserDocument> searchAllDocument(@RequestParam(value = "index") String index) throws IOException {
        log.info("索引为：{}", index);
        return searchService.searchAllDocument(index, UserDocument.class);
    }

    @PostMapping("searchDocumentByKey")
    public List<UserDocument> searchDocumentByKey(@RequestBody SearchDocumentByKeyVO documentByKeyVO) throws IOException {
        log.info("参数为：{}", documentByKeyVO);
        String index = documentByKeyVO.getIndex();
        String key = documentByKeyVO.getKey();
        Object value = documentByKeyVO.getValue();
        return searchService.searchDocumentByKey(index, key, value, UserDocument.class);
    }
}
