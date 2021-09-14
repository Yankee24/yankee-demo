package com.yankee.elasticsearch;

import com.yankee.elasticsearch.document.UserDocument;
import com.yankee.elasticsearch.service.ElasticSearchSearchService;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/13
 */
public class ElasticSearchSearchTest extends ElasticSearchApplicationTests {
    @Resource
    private ElasticSearchSearchService<UserDocument> searchService;

    @Test
    public void testGetDocumentList() throws IOException {
        System.out.println(searchService.searchAllDocument("user", UserDocument.class));
    }

    @Test
    public void testSearchDocumentByKey() throws IOException {
        System.out.println(searchService.searchDocumentByKey("user", "name", "yankee", UserDocument.class));
    }
}
