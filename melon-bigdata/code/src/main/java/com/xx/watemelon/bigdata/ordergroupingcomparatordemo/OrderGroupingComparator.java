package com.xx.watemelon.bigdata.ordergroupingcomparatordemo;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @description: 订单分组聚合器(根据规则分组, 之后分别执行reduce方法) !! 此包与order包的区别为outKey不同 ，reduce之前，执行OrderGroupingComparator，默认是按key分组
 * @author: xiesx
 * @createTime: 2018-12-05 16:36
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public class OrderGroupingComparator extends WritableComparator {

    /**
     * 写一个空参构造
     */
    public OrderGroupingComparator(){
        super(OrderBean.class, true);
    }


    /**
     * 重写比较方法，orderId相等的为一组
     * @param a
     * @param b
     * @return
     */
    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        String orderIdA = a.toString();
        String orderIdB = b.toString();
        return orderIdA.compareTo(orderIdB);
    }

}
