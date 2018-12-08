package com.xx.watemelon.bigdata.haddoop.mergetablereducejoin;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 表对象Reducer
 * @author: xiesx
 * @createTime: 2018-12-07 19:32
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public class TableReducer extends Reducer<Text, TableBean, TableBean, NullWritable> {

    @Override
    protected void reduce(Text inKey, Iterable<TableBean> inValues, Context context) throws IOException, InterruptedException {
        List<TableBean> tableBeanList = new ArrayList<>();
        TableBean pdTableBean = new TableBean();
        for (TableBean inValueTableBean : inValues) {
            if (inValueTableBean.getFlag().equals(1)) {
                TableBean tableBean = new TableBean();
                BeanUtils.copyProperties(inValueTableBean, tableBean);
                tableBeanList.add(tableBean);
            } else if (inValueTableBean.getFlag().equals(2)) {
                pdTableBean.setPName(inValueTableBean.getPName());
            }
        }
        for (TableBean tableBean : tableBeanList) {
            tableBean.setPId(pdTableBean.getPName());
            context.write(tableBean, NullWritable.get());
        }
    }

}
