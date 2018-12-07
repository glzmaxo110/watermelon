package com.xx.watemelon.bigdata.mergetablereducejoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @description: 表对象mapper
 * @author: xiesx
 * @createTime: 2018-12-07 17:55
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public class TableMapper extends Mapper<LongWritable, Text, Text, TableBean> {

    @Override
    protected void map(LongWritable inKey, Text inValue, Context context) throws IOException, InterruptedException {

        // 获取文件类型
        FileSplit split = (FileSplit) context.getInputSplit();
        String name = split.getPath().getName();

        String orderTableName = "order";
        String pdTableName = "pd";

        Text textOutKey = new Text();

        String line = inValue.toString();
        String[] lineValues = line.split("\t");
        if (name.startsWith(orderTableName)) {
            TableBean tableBean = new TableBean();
            tableBean.setOrderId(lineValues[0]);
            tableBean.setPId(lineValues[1]);
            tableBean.setAmount(Integer.valueOf(lineValues[2]));
            tableBean.setFlag(1);
            tableBean.setPName("");
            textOutKey.set(lineValues[1]);
            context.write(textOutKey, tableBean);
        } else if (name.startsWith(pdTableName)) {
            TableBean tableBean = new TableBean();
            tableBean.setPId(lineValues[0]);
            tableBean.setPName(lineValues[1]);
            tableBean.setFlag(2);
            tableBean.setAmount(0);
            tableBean.setOrderId("");
            textOutKey.set(lineValues[0]);
            context.write(textOutKey, tableBean);
        }
    }

}
