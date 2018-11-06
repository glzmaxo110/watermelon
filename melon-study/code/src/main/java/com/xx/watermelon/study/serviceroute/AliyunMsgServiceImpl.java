package com.xx.watermelon.study.serviceroute;

import org.springframework.stereotype.Service;

/**
 * @description: 阿里云短信接口实现(根据需要继承抽象类的中的方法)
 * @author: xiesx
 * @createTime: 2018-11-05 18:17
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
@Service("aliyunMsgService")
public class AliyunMsgServiceImpl extends AbstractBaseMsgService {

    @Override
    public String sendMessage(String phoneNumber, String content) {
        System.out.println("阿里云发送短信");
        return null;
    }

    @Override
    public String acceptReceipt(String msgId) {
        System.out.println("阿里云接收短信回执");
        return null;
    }

}