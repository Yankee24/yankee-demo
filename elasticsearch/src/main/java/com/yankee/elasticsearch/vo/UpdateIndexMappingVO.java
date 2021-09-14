package com.yankee.elasticsearch.vo;

import lombok.Data;

import java.util.Map;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/14
 */
@Data
public class UpdateIndexMappingVO {
    /**
     * 索引
     */
    private String index;

    /**
     * mapping
     */
    private Map<String, Object> mapping;
}
