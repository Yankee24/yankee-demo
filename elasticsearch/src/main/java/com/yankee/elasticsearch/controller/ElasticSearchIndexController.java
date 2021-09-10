package com.yankee.elasticsearch.controller;

import com.yankee.elasticsearch.service.EalsticSearchIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
