package com.xx.watemelon.bigdata.haddoop.mergetablecachefile;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 表合并缓存文件方式demo 3.4.2
 * @author: xiesx
 * @createTime: 2018-12-08 15:31
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public class TableCacheMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    Map<String, String> pdMap = new HashMap<>();

    /**
     * 重写预加载方法
     *
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("pd.txt"))));
        String line = bufferedReader.readLine();
        //  01	小米
        while (StringUtils.isNotBlank(line)) {
            String[] pdArr = line.split("\t");
            pdMap.put(pdArr[0], pdArr[1]);
        }
        // 关闭资源
        bufferedReader.close();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 1001	 01	 1
        String inValue = value.toString();
        String[] inValueArray = inValue.split("\t");

        Text k = new Text();
        k.set(value + " " + pdMap.get(inValueArray[1]));
        context.write(k, NullWritable.get());
    }

}
