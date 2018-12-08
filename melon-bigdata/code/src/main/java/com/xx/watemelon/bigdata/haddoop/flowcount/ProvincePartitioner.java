package com.xx.watemelon.bigdata.haddoop.flowcount;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;


/**
 * @author xiesx
 * @version 1.0
 * @description ProvincePartitioner
 * @copyright 2018-09-26 18:03
 */
public class ProvincePartitioner extends Partitioner<Text, FlowBean> {

    @Override
    public int getPartition(Text text, FlowBean flowBean, int numPartitions) {

        int partition = 4;

        // 根据手机号码的前三位不同来分成不同的分区
        String preNumber = text.toString().substring(0, 3);
        if ("136".equals(preNumber)) {
            partition = 0;
        } else if ("137".equals(preNumber)) {
            partition = 1;
        } else if ("138".equals(preNumber)) {
            partition = 2;
        } else if ("139".equals(preNumber)) {
            partition = 3;
        }
        return partition;
    }

}
