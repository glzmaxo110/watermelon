package com.xx.watemelon.bigdata.haddoop.flowcount;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author xiesx
 * @version 1.0
 * @description FlowBean 流量对象,需要实现Writable接口
 * @copyright 2018-09-12 11:01
 */
public class FlowBean implements Writable {

    /**
     * 上行流量
     */
    private long upFlow;
    /**
     * 下行流量
     */
    private long downFlow;
    /**
     * 总流量
     */
    private long sumFlow;

    /**
     * 序列化方法
     * @param out
     * @throws IOException
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(this.upFlow);
        out.writeLong(this.downFlow);
        out.writeLong(this.sumFlow);
    }

    /**
     * 反序列化方法
     * 反序列化方法读顺序必须和写序列化方法的写顺序必须一致
     * @param in
     * @throws IOException
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        this.upFlow = in.readLong();
        this.downFlow = in.readLong();
        this.sumFlow = in.readLong();
    }

    public long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    public long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(long downFlow) {
        this.downFlow = downFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }

    public FlowBean(long upFlow, long downFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = upFlow + downFlow;
    }

    /**
     * 空参构造器，反序列化的时候使用
     */
    public FlowBean() {

    }

    /**
     * 打印出来的格式
     * @return
     */
    @Override
    public String toString() {
        return upFlow + "\t" + downFlow + "\t" + sumFlow;
    }

}
