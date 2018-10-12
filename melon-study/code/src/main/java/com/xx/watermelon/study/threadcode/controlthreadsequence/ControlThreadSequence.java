package com.xx.watermelon.study.threadcode.controlthreadsequence;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiesx
 * @version 1.0
 * @description ControlThreadSequence 控制线程的执行顺序的两种方式：1.join； 2.线程池的singleThreadExecutor
 * @copyright 2018-10-10 11:52
 */
public class ControlThreadSequence {

    static Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("当前执行thread1");
        }
    });

    static Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("当前执行thread2");
        }
    });

    static Thread thread3 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("当前执行thread3");
        }
    });

    /*
        方式1
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        thread3.start();
        thread3.join();*/

    /*
    *   方式2
    *   ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(thread1);
        executorService.submit(thread2);
        executorService.submit(thread3);
        executorService.shutdown();
    * */

    public static void main(String[] args) throws InterruptedException {
        @SuppressWarnings("AlibabaThreadPoolCreation") ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(thread1);
        executorService.submit(thread2);
        executorService.submit(thread3);
        executorService.shutdown();
    }


}
