package com.yankee.elasticsearch.controller;

import com.yankee.elasticsearch.document.UserDocument;
import com.yankee.elasticsearch.service.ElasticSearchDocumentService;
import com.yankee.elasticsearch.vo.BulkCreateDocumentVO;
import com.yankee.elasticsearch.vo.CreateDocumentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/elasticsearch/document")
@Slf4j
public class ElasticSearchDocumentController {
    @Resource
    private ElasticSearchDocumentService<UserDocument> documentService;

    @PostMapping("create")
    public ResponseEntity<Boolean> createDocument(@RequestBody CreateDocumentVO<UserDocument> documentVO) throws IOException {
        log.info("参数为：{}", documentVO);
        String index = documentVO.getIndex();
        UserDocument document = documentVO.getDocument();
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.createDocument(index, document));
    }

    @PostMapping("bulkCreate")
    public ResponseEntity<Boolean> bulkCreateDocument(@RequestBody BulkCreateDocumentVO<UserDocument> documentVO) throws IOException {
        log.info("参数为：{}", documentVO);
        String index = documentVO.getIndex();
        List<UserDocument> documents = documentVO.getDocuments();
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.bulkCreateDocument(index, documents));
    }

    @GetMapping("info")
    public UserDocument getDocument(@RequestParam(value = "index") String index, @RequestParam(value = "id") String id) throws IOException {
        log.info("索引为：{}，作业ID：{}", index, id);
        return documentService.infoDocument(index, UserDocument.class, id);
    }

    @GetMapping("delete")
    public String deleteDocument(@RequestParam(value = "index") String index, @RequestParam(value = "id") String id) throws IOException {
        log.info("索引为：{}，作业ID：{}", index, id);
        return documentService.deleteDocument(index, id);
    }
}
