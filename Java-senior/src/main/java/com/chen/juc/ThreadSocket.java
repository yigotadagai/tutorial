package com.chen;

import java.util.concurrent.TimeUnit;

/**
 * @author Robert V
 * @create 2022-10-05-下午7:10
 */
public class ThreadSocket {
    /**
     * 线程通信
     */

    private Integer num = 0;

    public void sub() {
        synchronized (this) {
            try {
//                if (num <= 0) {
//                    //满足条件，线程进入等待
//                    this.wait();
//                }

                //换成while就不会产生虚假唤醒
                while (num <= 0) {
                    this.wait();
                }
                //(如果用if)不满足条件，线程减1,但是在不满足条件时，被等待后，其他线程已经更改了num的值，而减1操作还会继续执行，就产生虚假唤醒
                System.out.println(Thread.currentThread().getName() + --num);
                //唤醒其他所有线程
                this.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void add() {
        synchronized (this) {
            try {
                while (num > 0) {
                    this.wait();
                }
                System.out.println(Thread.currentThread().getName() + ++num);
                this.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadSocket threadSocket = new ThreadSocket();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                threadSocket.add();
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread01: ").start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                threadSocket.sub();
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "thread02: ").start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                threadSocket.add();
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "thread03: ").start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                threadSocket.sub();
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "thread04: ").start();
    }
}
