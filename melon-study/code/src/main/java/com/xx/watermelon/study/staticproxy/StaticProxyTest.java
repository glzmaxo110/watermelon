package com.xx.watermelon.study.staticproxy;

/**
 * @description: 静态代理测试类
 * @author: xiesx
 * @createTime: 2018-10-25 18:25
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public class StaticProxyTest {


    public static void main(String[] args) {

        ICalculator calculator = new ICalculatorImpl();
        ICalculator iCalculator = new CalculatorProxy(calculator);
        // 调用真实类的方法，并且执行代理类的前后方法，如记录日志，记录时间等
        int addResult = iCalculator.add(1, 2);
        System.out.println("====================");
        System.out.println(addResult);

    }

}
