package com.xx.watermelon.bigdata.test.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * @version 1.0, Created by xiesx on 2018-08-07 11:08.
 * @description com.xx.watermelon.bigdata.test.hdfs.HDFSClientTest 2018/8/7
 * @copyright 2018-08-07 11:08
 */
public class GetFileByStreamTest extends DemoTest {

    @Test
    public void getFileByStreamTest() {
        try {
            //1.获取配置信息
            Configuration config = new Configuration();
            FileSystem fs = FileSystem.get(new URI("hdfs://192.168.1.102:9000"), config, "xuan");

            //2.输入流
            InputStream inStream = fs.open(new Path("hdfs://192.168.1.102:9000/user/xuan/hello/hellowordStrem.txt"));

            //3.输出流
            OutputStream outStream = new FileOutputStream("E://hadoop.txt");

            //4.流对拷贝
            IOUtils.copyBytes(inStream, outStream, 4096, true);

            //5.流关闭
            IOUtils.closeStream(inStream);
            IOUtils.closeStream(outStream);
        }
       catch (Exception e) {
            e.printStackTrace();
        }
    }
}
