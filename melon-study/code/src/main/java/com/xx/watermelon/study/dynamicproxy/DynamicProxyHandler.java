package com.xx.watermelon.study.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @description: 动态代理handler
 * @author: xiesx
 * @createTime: 2018-10-25 19:07
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public class DynamicProxyHandler implements InvocationHandler {

    private Object obj;

    public DynamicProxyHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        this.before();
        Object invokeResult = method.invoke(obj, args);
        this.after();
        return invokeResult;
    }

    private void before() {
        System.out.println("真实方法执行之前的操作");
    }

    private void after() {
        System.out.println("真实方法执行之后的操作");
    }


}
