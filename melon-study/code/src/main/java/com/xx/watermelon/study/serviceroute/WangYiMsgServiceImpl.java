package com.xx.watermelon.study.serviceroute;

import org.springframework.stereotype.Service;

/**
 * @description: 网易短信接口实现(根据需要继承抽象类的中的方法)
 * @author: xiesx
 * @createTime: 2018-11-05 18:17
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
@Service("wangYiMsgService")
public class WangYiMsgServiceImpl extends AbstractBaseMsgService {

    @Override
    public String sendMessage(String phoneNumber, String content) {
        System.out.println("网易发送短信");
        return null;
    }

    @Override
    public String acceptReceipt(String msgId) {
        System.out.println("网易接收短信回执");
        return null;
    }

}