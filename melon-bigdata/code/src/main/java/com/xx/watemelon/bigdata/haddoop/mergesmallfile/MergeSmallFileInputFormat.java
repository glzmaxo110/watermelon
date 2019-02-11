package com.xx.watemelon.bigdata.haddoop.mergesmallfile;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

/**
 * @description: 合并小文件自定义inputFormat
 * @author: xiesx
 * @createTime: 2018-12-10 16:04
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public class MergeSmallFileInputFormat extends FileInputFormat<NullWritable, BytesWritable> {

    @Override
    public RecordReader<NullWritable, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {

        // 1 定义一个自己的recordReader
        MergeSmallFileRecordReader mergeSmallFileRecordReader = new MergeSmallFileRecordReader();

        // 2 初始化recordReader
        mergeSmallFileRecordReader.initialize(split, context);

        return mergeSmallFileRecordReader;
    }

}
