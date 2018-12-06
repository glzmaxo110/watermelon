package com.xx.watemelon.bigdata.ordergroupingcomparatordemo;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @description: 订单Mapper,此包与order包的区别为outKey不同
 * @author: xiesx
 * @createTime: 2018-12-05 17:45
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public class OrderMapper extends Mapper<LongWritable, Text, Text, Text> {

    // 因为GroupingComparator是根据key分组，所以outKey就是GroupingComparator重写方法里面的参数，详见流程图

    /**
     * 重写map方法
     * @param key 行号
     * @param value  行值
     * @param context map执行的上下文对象
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // 一行的数据
        String valueStr = value.toString();
        String[] values = valueStr.split("\t");
        // 一行的数据处理
        Text outKey = new Text();
        Text outValue = new Text();
        // orderId
        outKey.set(values[0]);
        // price
        outValue.set(values[2]);

        context.write(outKey, outValue);
    }

}