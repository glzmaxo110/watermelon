package com.xx.watemelon.bigdata.haddoop.ordergroupingcomparatordemo;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @description: 订单结果分区
 * @author: xiesx
 * @createTime: 2018-12-05 16:59
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public class OrderPartitioner extends Partitioner<Text, Text> {

    @Override
    public int getPartition(Text reducerOutKey, Text reducerOutValue, int numPartitions) {
        //决定分成几个task
        return (reducerOutKey.hashCode() & Integer.MAX_VALUE) % numPartitions;
    }

}
