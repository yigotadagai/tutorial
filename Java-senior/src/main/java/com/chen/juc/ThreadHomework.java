package com.chen.juc;

import java.util.concurrent.TimeUnit;

/**
 * @author Robert V
 * @create 2022-10-05-下午6:30
 */
public class ThreadHomework {
    /**
     * 作业：
     * 	1、创建多线程
     * 		继承Thread类
     * 		实现Runnable接口
     * 		Callable+FutureTask
     * 	2、卖票的例子
     * 	3、线程间通信
     */

    public static void main(String[] args) {
        //1.集成thread创建多线程
        Animal cat = new Animal();
        Thread thread = new Thread(cat);
//        thread.start();

        //2.实现Runnable接口
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + i);
            }
        }, "线程1：").start();

    }
}

class Animal extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("吃鱼" + Thread.currentThread().getName() + i);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}