package com.xx.watemelon.bigdata.haddoop.order;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author xiesx
 */
public class OrderGroupingComparator extends WritableComparator {

	/**
	 * 写一个空参构造
	 */
	public OrderGroupingComparator(){
		super(OrderBean.class, true);
	}

	/**
	 * 重写比较的方法
	 * @param a
	 * @param b
	 * @return
	 */
	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		OrderBean aBean = (OrderBean) a;
		OrderBean bBean = (OrderBean) b;

		// 根据订单id号比较，判断是否是一组
		return aBean.getOrderId().compareTo(bBean.getOrderId());
	}
}
