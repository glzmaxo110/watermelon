package com.xx.watemelon.bigdata.haddoop.mergetablecachefile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.net.URI;

/**
 * @description: 表缓存文件demo 3.4.2
 * @author: xiesx
 * @createTime: 2018-12-07 17:45
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public class TableCacheDriver {

	public static void main(String[] args) throws Exception {

		// 1 获取job信息
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration);

		// 2 设置加载jar包路径
		job.setJarByClass(TableCacheDriver.class);

		// 3 关联map
		job.setMapperClass(TableCacheMapper.class);

		// 4 设置最终输出数据类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);

		// 5 设置输入输出路径
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		// 6 加载缓存数据
		job.addCacheFile(new URI("file:/e:/cache/pd.txt"));

		// 7 map端join的逻辑不需要reduce阶段，设置reducetask数量为0
		job.setNumReduceTasks(0);

		// 8 提交
		boolean result = job.waitForCompletion(true);
		System.exit(result ? 0 : 1);

	}
}
