package com.xx.watemelon.bigdata.flowsortcount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author xiesx
 * @version 1.0
 * @description FlowCountMapper 流量统计对象
 * @copyright 2018-09-12 11:41
 */
public class FlowCountMapper extends Mapper<LongWritable, Text, Text, FlowBean> {

    /**
     * 覆写父类的map方法
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        Text outKey = new Text();
        FlowBean outValue = new FlowBean();

        //行值
        String line = value.toString();

        String[] lineStrArray = line.split("\t");

        outKey.set(lineStrArray[1]);
        outValue.setUpFlow(Long.parseLong(lineStrArray[lineStrArray.length - 3]));
        outValue.setDownFlow(Long.parseLong(lineStrArray[lineStrArray.length - 2]));

        //写入
        context.write(outKey, new FlowBean(outValue.getUpFlow(), outValue.getDownFlow()));

    }

}
