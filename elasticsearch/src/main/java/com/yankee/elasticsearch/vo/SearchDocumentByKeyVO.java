package com.yankee.elasticsearch.vo;

import lombok.Data;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/14
 */
@Data
public class SearchDocumentByKeyVO {
    /**
     * 索引
     */
    private String index;

    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private Object value;
}
