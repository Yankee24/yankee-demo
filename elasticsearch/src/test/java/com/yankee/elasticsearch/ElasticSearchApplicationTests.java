package com.yankee.elasticsearch;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Yankee
 * @program yankee-demo
 * @description
 * @since 2021/9/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ElasticSearchApplicationTests {
    @Before
    public void init() {
        System.out.println("开始测试......");
    }

    @After
    public void after() {
        System.out.println("测试结束......");
    }
}
