package com.yankee.elasticsearch;

import com.yankee.elasticsearch.document.Document;
import com.yankee.elasticsearch.document.UserDocument;
import com.yankee.elasticsearch.service.ElasticSearchDocumentService;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/13
 */
public class ElasticSearchDocumentTest extends ElasticSearchApplicationTests {
    @Resource
    private ElasticSearchDocumentService<UserDocument> documentService;

    @Test
    public void testCreateDocument() throws IOException {
        UserDocument userDocument = UserDocument.builder()
                .name("yankee")
                .sex("男")
                .age(23)
                .city("陕西")
                .build();
        System.out.println(documentService.createDocument("user", userDocument));
    }

    @Test
    public void testBulkCreateDocument() throws IOException {
        List<UserDocument> documents = new ArrayList<>();
        documents.add(UserDocument.builder()
                .name("王五")
                .sex("女")
                .age(21)
                .city("重庆")
                .build());
        documents.add(UserDocument.builder()
                .name("奥特曼")
                .sex("男")
                .age(24)
                .city("M78")
                .build());
        System.out.println(documentService.bulkCreateDocument("user", documents));
    }

    @Test
    public void testGetDocument() throws IOException {
        Document user = documentService.getDocument("user", UserDocument.class, "8c4dd41a-12aa-4c98-a254-392f6f8733c1");
        System.out.println(user);
    }

    @Test
    public void testDeleteDocument() throws IOException {
        String user = documentService.deleteDocument("user", "2");
        System.out.println(user);
    }

    @Test
    public void testUpdateDocument() throws IOException {
        Map<String, Object> document = new HashMap<>();
        document.put("age", 22);
        System.out.println(documentService.updateDocument("user", "78af0186-dd29-42ae-aad6-7634a19c45b1", document));
    }
}
