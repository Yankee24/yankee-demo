package com.yankee.elasticsearch.controller;

import com.yankee.elasticsearch.document.UserDocument;
import com.yankee.elasticsearch.service.ElasticSearchDocumentService;
import com.yankee.elasticsearch.vo.BulkCreateDocumentVO;
import com.yankee.elasticsearch.vo.CreateDocumentVO;
import com.yankee.elasticsearch.vo.UpdateDocumentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/13
 */
@RestController
@RequestMapping("/elasticsearch/user")
@Slf4j
public class ElasticSearchDocumentController {
    @Resource
    private ElasticSearchDocumentService<UserDocument> documentService;

    @PostMapping("createDocument")
    public ResponseEntity<Boolean> createDocument(@RequestBody CreateDocumentVO<UserDocument> documentVO) throws IOException {
        log.info("参数为：{}", documentVO);
        String index = documentVO.getIndex();
        UserDocument document = documentVO.getDocument();
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.createDocument(index, document));
    }

    @PostMapping("bulkCreateDocument")
    public ResponseEntity<Boolean> bulkCreateDocument(@RequestBody BulkCreateDocumentVO<UserDocument> documentVO) throws IOException {
        log.info("参数为：{}", documentVO);
        String index = documentVO.getIndex();
        List<UserDocument> documents = documentVO.getDocuments();
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.bulkCreateDocument(index, documents));
    }

    @GetMapping("infoDocument")
    public UserDocument getDocument(@RequestParam(value = "index") String index, @RequestParam(value = "id") String id) throws IOException {
        log.info("索引为：{}，作业ID：{}", index, id);
        return documentService.infoDocument(index, UserDocument.class, id);
    }

    @GetMapping("deleteDocument")
    public String deleteDocument(@RequestParam(value = "index") String index, @RequestParam(value = "id") String id) throws IOException {
        log.info("索引为：{}，作业ID：{}", index, id);
        return documentService.deleteDocument(index, id);
    }

    @PostMapping("updateDocument")
    public String updateDocument(@RequestBody UpdateDocumentVO documentVO) throws IOException {
        log.info("参数为：{}", documentVO);
        String index = documentVO.getIndex();
        String id = documentVO.getId();
        Map<String, Object> document = documentVO.getDocument();
        return documentService.updateDocument(index, id, document);
    }
}
