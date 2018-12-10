package com.xx.watemelon.bigdata.haddoop.mergetablecachefile;

import lombok.Data;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @description: 表对象（表合并demo 3.4.2）
 * @author: xiesx
 * @createTime: 2018-12-07 17:45
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
@Data
public class TableBean implements Writable {

    /**
     * 订单id
     */
    private String orderId;
    /**
     * 产品Id
     */
    private String pId;
    /**
     * 数量
     */
    private int amount;
    /**
     * 产品名称
     */
    private String pName;
    /**
     * 表的标记，1.订单表，2.产品表
     */
    private Integer flag;

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.orderId);
        out.writeUTF(this.pId);
        out.writeInt(this.amount);
        out.writeUTF(this.pName);
        out.writeInt(this.flag);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.orderId = in.readUTF();
        this.pId = in.readUTF();
        this.amount = in.readInt();
        this.pName = in.readUTF();
        this.flag = in.readInt();
    }

}
