package com.xx.watermelon.study.dynamicproxy;

import com.xx.watermelon.study.staticproxy.ICalculator;
import com.xx.watermelon.study.staticproxy.ICalculatorImpl;

import java.lang.reflect.Proxy;

/**
 * @description: 动态代理类测试
 * @author: xiesx
 * @createTime: 2018-10-25 19:05
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public class DynamicProxyTest {

    public static void main(String[] args) {

        ICalculator calculator = new ICalculatorImpl();

        DynamicProxyHandler dynamicProxyHandler = new DynamicProxyHandler(calculator);

        ICalculator calculatorProxy = (ICalculator) Proxy.newProxyInstance(calculator.getClass().getClassLoader(), calculator.getClass().getInterfaces(), dynamicProxyHandler);

        int addResult = calculatorProxy.add(2, 2);

        System.out.println("=====================");

        System.out.println(addResult);

    }

}