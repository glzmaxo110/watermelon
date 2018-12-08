package com.xx.watemelon.bigdata.haddoop.order;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author xiesx
 * @version 1.0
 * @description FlowCountDriver 流量统计驱动
 * @copyright 2018-09-12 15:22
 */
public class MyOrderDriver {


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        System.setProperty("HADOOP_USER_NAME", "xuan");

        Configuration config = new Configuration();
        //设置jar包本地位置
        config.set("mapreduce.job.jar", "E:\\watermelon\\melon-bigdata\\code\\target\\melon-bigdata-0.0.1-SNAPSHOT.jar");

        //先删除output目录
        deleteDir(config, "/user/xuan/ordergroupcomparatoroutput");

        //获取job对象
        Job job = Job.getInstance(config);

        // 设置jar包
        job.setJarByClass(MyOrderDriver.class);

        // 设置mapper,reducer类
        job.setMapperClass(OrderMapper.class);
        job.setReducerClass(OrderReducer.class);

        // 4 设置map输出数据key和value类型
        job.setMapOutputKeyClass(OrderBean.class);
        job.setMapOutputValueClass(NullWritable.class);

        // 5 设置最终输出数据的key和value类型
        job.setOutputKeyClass(OrderBean.class);
        job.setOutputValueClass(NullWritable.class);

        // 设置
        job.setGroupingComparatorClass(OrderGroupingComparator.class);

        // 设置输入输出路径
        FileInputFormat.setInputPaths(job, new Path("/user/xuan/groupcomparator"));
        FileOutputFormat.setOutputPath(job, new Path("/user/xuan/ordergroupcomparatoroutput"));

        //设置了自定义分区之后，一定要设置分区数，否则报错
        job.setPartitionerClass(OrderPatitioner.class);
        // 如果小于实际分区数，则java.io.IOException：Illegal partition for
        // 如果多于实际分区数，则多出来的输出文件为空
        job.setNumReduceTasks(3);

        boolean result = job.waitForCompletion(true);
        System.out.println(result);
        // status是非零参数，那么表示是非正常退出。
        System.exit(result ? 0 : 1);

    }

    /**
     * 删除指定目录
     * @param conf
     * @param dirPath
     * @throws IOException
     *
     */
    private static void deleteDir(Configuration conf, String dirPath) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path targetPath = new Path(dirPath);
        if (fs.exists(targetPath)) {
            boolean delResult = fs.delete(targetPath, true);
            if (delResult) {
                System.out.println(targetPath + " has been deleted sucessfullly.");
            } else {
                System.out.println(targetPath + " deletion failed.");
            }
        }
    }
}