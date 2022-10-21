package com.chen;

import java.util.concurrent.TimeUnit;

/**
 * @author Robert V
 * @create 2022-10-05-下午6:55
 */
public class SaleTicket {
    private static Integer num = 100;

    public static void main(String[] args) {
        SaleTicket saleTicket = new SaleTicket();

        new Thread(() -> {
            sale();
        }, "thread1: ").start();

        new Thread(() -> {
            sale();
        }, "thread2: ").start();

        new Thread(() -> {
            sale();
        }, "thread3: ").start();
    }

    public static void sale() {
        for (int i = 0; i < 50; i++) {
            synchronized (SaleTicket.class) {
                try {
                    if (num > 0) {
                        System.out.println(Thread.currentThread().getName() + "剩余票数为: " + num--);
                        TimeUnit.MILLISECONDS.sleep(100);
                    } else {
                        System.out.println("票已售完");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
