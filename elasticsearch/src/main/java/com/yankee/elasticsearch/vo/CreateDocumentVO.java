package com.yankee.elasticsearch.vo;

import com.yankee.elasticsearch.document.Document;
import lombok.Data;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/14
 */
@Data
public class CreateDocumentVO<T extends Document> {
    /**
     * 索引
     */
    private String index;

    /**
     * 文档
     */
    private T document;
}
