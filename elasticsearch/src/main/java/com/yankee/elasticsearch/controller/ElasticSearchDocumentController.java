package com.yankee.elasticsearch.controller;

import com.yankee.elasticsearch.document.UserDocument;
import com.yankee.elasticsearch.service.ElasticSearchDocumentService;
import com.yankee.elasticsearch.vo.CreateDocumentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

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
    private ElasticSearchDocumentService documentService;

    @PostMapping("create")
    public ResponseEntity<Boolean> createDocument(@RequestBody CreateDocumentVO<UserDocument> documentVO) throws IOException {
        log.info("参数为：{}", documentVO);
        String index = documentVO.getIndex();
        UserDocument document = documentVO.getDocument();
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.createDocument(index, document));
    }
}
