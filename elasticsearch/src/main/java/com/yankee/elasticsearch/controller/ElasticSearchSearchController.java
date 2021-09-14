package com.yankee.elasticsearch.controller;

import com.yankee.elasticsearch.document.UserDocument;
import com.yankee.elasticsearch.service.ElasticSearchSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/elasticsearch/search")
@Slf4j
public class ElasticSearchSearchController {
    @Resource
    private ElasticSearchSearchService<UserDocument> searchService;

    @GetMapping("all")
    public List<UserDocument> searchAllDocument(@RequestParam(value = "index") String index) throws IOException {
        log.info("索引为：{}", index);
        return searchService.searchAllDocument(index, UserDocument.class);
    }
}
