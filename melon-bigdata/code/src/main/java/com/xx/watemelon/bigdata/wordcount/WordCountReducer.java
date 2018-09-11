package com.xx.watemelon.bigdata.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author xiesx
 * @version 1.0
 * @description WordCountReducer 2018/8/29
 * @copyright 2018-08-29 15:05
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    /**
     * 重写reduce方法(按key分组，reduce按key循环)
     * @param key     重复key
     * @param values  key的values值
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        // 传入的数据已经是按key排序并分组的了，只需遍历value即可
        Text outKey = new Text();
        IntWritable outValue = new IntWritable();

        int sum = 0;
        for (IntWritable value : values) {
            sum += value.get();
        }
        outKey.set(key);
        outValue.set(sum);
        context.write(outKey, outValue);

    }

}
