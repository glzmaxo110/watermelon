package com.xx.watermelon.bigdata.test.hdfs;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @version 1.0, Created by xiesx on 2018-08-07 10:48.
 * @description HdfsTest 2018/8/7
 * @author xiesx
 * @copyright 2018-08-07 10:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootConfiguration
public class DemoTest {

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }

}
