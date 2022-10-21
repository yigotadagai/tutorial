package com.chen.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Robert V
 * @create 2022-10-06-下午2:14
 */
public class LockTest {

    private Integer ticket = 100;

    private Lock lock = new ReentrantLock();

    public void sale() {
        lock.lock();
        try {
            if (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + ticket--);
            } else {
                System.out.println("票已卖完");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockTest lockTest = new LockTest();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                lockTest.sale();
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread-1: ").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                lockTest.sale();
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread-2: ").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                lockTest.sale();
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread-3: ").start();
    }

}
