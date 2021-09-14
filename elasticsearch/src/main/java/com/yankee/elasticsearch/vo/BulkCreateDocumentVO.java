package com.yankee.elasticsearch.vo;

import com.yankee.elasticsearch.document.Document;
import lombok.Data;

import java.util.List;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/14
 */
@Data
public class BulkCreateDocumentVO<T extends Document> {
    /**
     * 索引
     */
    private String index;

    /**
     * documents
     */
    private List<T> documents;
}
