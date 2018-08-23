package com.xx.watermelon.bigdata.test.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.net.URI;

/**
 * @version 1.0, Created by xiesx on 2018-08-07 11:08.
 * @description com.xx.watermelon.bigdata.test.hdfs.HDFSClientTest 2018/8/7
 * @copyright 2018-08-07 11:08
 */
public class RenameFileTest extends DemoTest {

    @Test
    public void renameFileTest() {
        Configuration config = new Configuration();
        try {
            FileSystem fs = FileSystem.get(new URI("hdfs://192.168.1.102:9000"), config, "xuan");
            //fs.copyFromLocalFile(new Path("e:/helloword.txt"), new Path("/user/xuan/helloword/helloword.txt"));
            //fs.copyToLocalFile(new Path("hdfs://192.168.1.102:9000/user/xuan/hello/helloword.txt"), new Path("d:/hello.txt"));
            //fs.rename(new Path("hdfs://192.168.1.102:9000/user/xuan/helloword/hellowordlololo.txt"),new Path("hdfs://192.168.1.102:9000/user/xuan/helloword/xxxx.txt"));
            fs.copyToLocalFile(new Path("hdfs://192.168.1.102:9000/user/xuan/helloword/xxxx.txt"),new Path("d:/xxxx.txt"));
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
