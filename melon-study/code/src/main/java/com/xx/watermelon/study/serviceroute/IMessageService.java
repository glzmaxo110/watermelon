package com.xx.watermelon.study.serviceroute;

/**
 * @description: 消息接口
 * @author: xiesx
 * @createTime: 2018-11-05 18:16
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public interface IMessageService {

    /**
     * 发送短消息
     * @param phoneNumber 短信接收电话号码
     * @param content 短信内容
     * @return 发送结果
     */
    String sendMessage(String phoneNumber, String content);

    /**
     * 接收消息回执
     * @param msgId 消息ID
     * @return 消息回执内容
     */
    String acceptReceipt(String msgId);

}
