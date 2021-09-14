package com.yankee.elasticsearch.vo;

import lombok.Data;

import java.util.Map;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/13
 */
@Data
public class CreateIndexVO {
    /**
     * 索引名称
     */
    private String index;

    /**
     * 索引的setting
     */
    private Map<String, Object> setting;

    /**
     * 索引的mapping
     */
    private Map<String, Object> mapping;
}
