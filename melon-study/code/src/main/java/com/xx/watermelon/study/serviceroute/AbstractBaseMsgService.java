package com.xx.watermelon.study.serviceroute;

/**
 * @description: 消息抽象类, 接口适配器：普通子类根据需要继承的方法自主选择
 * 接口适配器使用场景：
 * 想要使用接口中的某个或某些方法，但是接口中有太多方法，我们要使用时必须实现接口并实现其中的所有方法，可以使用抽象类来实现接口，并不对方法进行实现（仅置空），
 * 然后我们再继承这个抽象类来通过重写想用的方法的方式来实现。这个抽象类就是适配器。
 * @author: xiesx
 * @createTime: 2018-11-05 18:23
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public abstract class AbstractBaseMsgService implements IMessageService {

    /**
     * 空实现，供继承该抽象类的子类自由选择需要覆写的方法(此为适配,不需要子类覆写的方法)
     * @param obj 待校验的参数
     */
    @Override
    public void checkParams(Object obj) {
        System.out.println("抽象类校验公共参数");
    }

    /**
     * 空实现，供继承该抽象类的子类自由选择需要覆写的方法
     * @param phoneNumber 短信接收电话号码
     * @param content 短信内容
     * @return
     */
    @Override
    public String sendMessage(String phoneNumber, String content) {
        return null;
    }

    /**
     * 空实现，供继承该抽象类的子类自由选择需要覆写的方法
     * @param msgId 消息ID
     * @return
     */
    @Override
    public String acceptReceipt(String msgId) {
        return null;
    }

}