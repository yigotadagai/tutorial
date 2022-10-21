package com.chen.juc;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Robert V
 * @create 2022-10-07-上午9:36
 */
public class WriteLockTest {
    private static ReentrantReadWriteLock lock1 = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock lock2 = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.WriteLock writeLock1 = lock1.writeLock();
    private static ReentrantReadWriteLock.WriteLock writeLock2 = lock2.writeLock();
    private static ReentrantReadWriteLock.ReadLock readLock = lock1.readLock();


    public static void main(String[] args) {

        try {

            writeLock1.lock();

            readLock.lock();

            System.out.println("写锁1加锁");

            writeLock2.lock();
            System.out.println("写锁2开始");

            System.out.println("写锁2结束");
            System.out.println("写锁1结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock1.unlock();
            writeLock2.unlock();
        }
    }
}
