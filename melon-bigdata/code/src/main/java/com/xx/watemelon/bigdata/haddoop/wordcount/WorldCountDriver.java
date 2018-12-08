package com.xx.watemelon.bigdata.haddoop.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author xiesx
 * @version 1.0,
 * @description WorldCountDriver 2018/8/29
 * @copyright 2018-08-29 15:52
 */
public class WorldCountDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {


        //在本机调试
        //System.setProperty("HADOOP_USER_NAME", "xuan");

        //conf
        Configuration config = new Configuration();
        //设置jar包本地位置
        config.set("mapreduce.job.jar", "E:\\watermelon\\melon-bigdata\\code\\target\\melon-bigdata-0.0.1-SNAPSHOT.jar");

        //先删除output目录
        deleteDir(config, "/user/xuan/wcoutput");

        //job
        Job job = Job.getInstance(config);

        //设置jar
        job.setJarByClass(WorldCountDriver.class);

        //设置mapper，reducer
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        //设置输入输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 切片优化，使用CombineTextInputFormat.class替代默认的TextInputFormat.class，将文件合并之后再进行mapTask
        // 如果不设置InputFormat，它默认用的是TextInputFormat.class
        job.setInputFormatClass(CombineTextInputFormat.class);
        // 4m
        CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);
        // 2m
        CombineTextInputFormat.setMinInputSplitSize(job, 2097152);

        // 设置输入输出路径
        FileInputFormat.setInputPaths(job, new Path("/user/xuan/wcinput"));
        FileOutputFormat.setOutputPath(job, new Path("/user/xuan/wcoutput"));
        //提交
        boolean result = job.waitForCompletion(true);
        System.out.println(result);
        // status是非零参数，那么表示是非正常退出。
        System.exit(result ? 0 : 1);
        System.out.println("------------");
    }

    /**
     * 删除指定目录
     *
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
