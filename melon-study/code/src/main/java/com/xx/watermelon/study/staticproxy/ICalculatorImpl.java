package com.xx.watermelon.study.staticproxy;

/**
 * @description: 计算器真实实现类
 * @author: xiesx
 * @createTime: 2018-10-25 17:30
 * @version: 1.0.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public class ICalculatorImpl implements ICalculator {

    @Override
    public int add(int a, int b) {
        System.out.println("a + b = ???");
        return a + b;
    }

}
