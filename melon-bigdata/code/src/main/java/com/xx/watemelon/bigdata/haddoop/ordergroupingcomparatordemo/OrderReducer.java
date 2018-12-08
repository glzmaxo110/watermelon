package com.xx.watemelon.bigdata.haddoop.ordergroupingcomparatordemo;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 订单Reducer
 * @author: xiesx
 * @createTime: 2018-12-05 18:38
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public class OrderReducer extends Reducer<Text, Text, Text, Text> {

    /**
     * 重写reduce方法(未使用orderBean对象，无法排序)
     *
     * @param key     订单号
     * @param values  价格
     * @param context reduce执行的上下文对象
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Text valueOut = new Text();
        List<Text> valueList = new ArrayList<Text>();
        for (Text text : values) {
            Text textValue = new Text();
            textValue.set(text);
            valueList.add(textValue);
        }
        valueOut.set(valueList.get(0));
        context.write(key, valueOut);
    }

}
