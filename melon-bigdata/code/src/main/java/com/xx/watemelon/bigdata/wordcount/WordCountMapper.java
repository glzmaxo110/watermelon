package com.xx.watemelon.bigdata.wordcount;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author xiesx
 * @version 1.0, Created by xiesx on 2018-08-29 14:18.
 * @description WordCountMapper 2018/8/29
 * @copyright 2018-08-29 14:18
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    /**
     * 重写map方法
     * @param key 行数
     * @param value  行内容
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        Text outKey = new Text();
        IntWritable outValue = new IntWritable();
        // 每行的数据
        String lineData = value.toString();

        //将行数据按逗号分隔
        String[] split = lineData.split(" ");
        for (String word : split) {
            outKey.set(word);
            outValue.set(1);
        }
        context.write(outKey,outValue);
    }

}
