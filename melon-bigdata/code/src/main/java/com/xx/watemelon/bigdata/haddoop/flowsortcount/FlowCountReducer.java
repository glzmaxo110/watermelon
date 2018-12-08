package com.xx.watemelon.bigdata.haddoop.flowsortcount;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author xiesx
 * @version 1.0
 * @description FlowCountReducer 流量统计Reducer
 * @copyright 2018-09-12 14:55
 */
public class FlowCountReducer extends Reducer<Text, FlowBean, Text, FlowBean> {

    /**
     * 覆写reduce方法
     *
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {

        // 按key分组之后，将values遍历出来累加上行,下行，总流量
        // 总上行流量
        long sumUpFlow = 0;
        // 总下行流量
        long sumDownFlow = 0;
        // 总下行流量
        long sumSumFlow = 0;

        for (FlowBean flowBean : values) {
            sumUpFlow += flowBean.getUpFlow();
            sumDownFlow += flowBean.getDownFlow();
            sumSumFlow += flowBean.getSumFlow();
        }
        // 使用FlowBean封装输出对象
        // 写出
        context.write(key, new FlowBean(sumUpFlow, sumDownFlow));
    }

}
