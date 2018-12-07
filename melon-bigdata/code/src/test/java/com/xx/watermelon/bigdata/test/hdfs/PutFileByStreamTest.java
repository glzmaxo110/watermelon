package com.xx.watermelon.bigdata.test.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * @version 1.0, Created by xiesx on 2018-08-07 11:08.
 * @description com.xx.watermelon.bigdata.test.hdfs.HDFSClientTest 2018/8/7
 * @copyright 2018-08-07 11:08
 */
public class PutFileByStreamTest extends DemoTest {

    @Test
    public void putFileByStreamTest() {
        try {
            //1.获取配置信息
            Configuration config = new Configuration();
            FileSystem fs = FileSystem.get(new URI("hdfs://192.168.1.102:9000"), config, "xuan");
            //2.输入流
            File file = new File("e:/PersonalData/bigDataTest/mergetable/pd.txt");

            InputStream inStream = (InputStream) new FileInputStream(file);
            //3.输出流
            Path outPutPath = new Path("hdfs://192.168.1.102:9000/user/xuan/mergetable/pd.txt");
            OutputStream outputStream = fs.create(outPutPath).getWrappedStream();
            //4.流对接
            IOUtils.copyBytes(inStream, outputStream, config); //此方法包含流关闭的方法
            //5.流关闭
            IOUtils.closeStream(inStream);
            IOUtils.closeStream(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
