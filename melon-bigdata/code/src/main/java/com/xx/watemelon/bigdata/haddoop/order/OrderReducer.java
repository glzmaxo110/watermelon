package com.xx.watemelon.bigdata.haddoop.order;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author xiesx
 */
public class OrderReducer extends Reducer<OrderBean, NullWritable, OrderBean, NullWritable>{

	@Override
	protected void reduce(OrderBean bean, Iterable<NullWritable> values,
						  Context context)	throws IOException, InterruptedException {

		// 写出
		context.write(bean, NullWritable.get());
	}
}
