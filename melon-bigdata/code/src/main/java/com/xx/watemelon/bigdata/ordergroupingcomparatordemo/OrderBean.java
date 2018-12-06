package com.xx.watemelon.bigdata.ordergroupingcomparatordemo;

import lombok.Data;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @description: 订单对象！！此包与order包的区别为outKey不同(选出最贵的订单,groupingcomparator例子)
 * @author: xiesx
 * @createTime: 2018-12-05 15:25
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
@Data
public class OrderBean implements WritableComparable<OrderBean> {

    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 订单价格
     */
    private Double price;

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.orderId);
        out.writeDouble(this.price);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.orderId = in.readUTF();
        this.price = in.readDouble();
    }

    /**
     * 排序（先按orderId升序排，再按price降序排）
     * @param orderBean
     * @return
     */
    @Override
    public int compareTo(OrderBean orderBean) {
        // 小的this在前，所以按orderId升序
        int result = this.orderId.compareTo(orderBean.getOrderId());
        // 如果相同再按price降序排
        if (result == 0) {
            result = this.price > orderBean.getPrice() ? -1 : 1;
            return result;
        }
        return result;
    }

}