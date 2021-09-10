package com.yankee.elasticsearch;

import com.yankee.elasticsearch.service.EalsticSearchIndexService;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/10
 */
public class ElasticSearchIndexTest extends ElasticSearchApplicationTests {
    @Resource
    private EalsticSearchIndexService indexService;

    @Test
    public void testExistIndex() throws IOException {
        Boolean aBoolean = indexService.existIndex("kibana_sample_data_logs");
        System.out.println(aBoolean);
    }
}
