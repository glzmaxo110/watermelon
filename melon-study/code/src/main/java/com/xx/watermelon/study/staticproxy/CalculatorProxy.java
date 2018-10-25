package com.xx.watermelon.study.staticproxy;

/**
 * @description: 计算器代理类
 * @author: xiesx
 * @createTime: 2018-10-25 17:36
 * @version: 1.0
 * @Copyright: xiesx
 * @modify: xiesx
 **/
public class CalculatorProxy implements ICalculator {

    private ICalculator calculator;

    /**
     * 此构造器为了传入接口对象，防止26行calculator对象为空
     * @param calculator
     */
    public CalculatorProxy(ICalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public int add(int a, int b) {
        System.out.println("代理执行加法之前可执行，例如：整理待计算的数据");
        //ICalculator calculator = new ICalculatorImpl(); 若无构造器，需要此行
        int addResult = calculator.add(a, b);
        System.out.println("代理执行加法之后可执行，例如：美化数据结果");
        return addResult;
    }

}