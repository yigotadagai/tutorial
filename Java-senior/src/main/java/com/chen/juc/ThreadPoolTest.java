package com.chen.juc;

import java.util.concurrent.*;

/**
 * @author Robert V
 * @create 2022-10-10-下午2:34
 */
public class ThreadPoolTest {

    public static void main(String[] args) {

        /**
         *      int corePoolSize	核心线程数
         * 			核心线程：即使空闲时间达到最大值也不会被释放
         * 			非核心线程：到达最大空闲时间会被释放
         *
         * 		int maximumPoolSize	最大线程数，整个线程池能够容纳的线程数量最多是多少
         * 		long keepAliveTime,	非核心线程最大空闲时间的数量
         * 		TimeUnit unit,		非核心线程最大空闲时间的单位
         * 		BlockingQueue<Runnable> workQueue,	存放等待中任务的阻塞队列
         * 		ThreadFactory threadFactory,		创建线程对象的工厂
         * 		RejectedExecutionHandler handler	拒绝策略，当线程池达到能力极限时，启用拒绝策略
         * 			线程数达到最大
         * 			等待队列已满
         */
        int corePoolSize = 5;
        int maximumPoolSize = 7;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                unit, workQueue, threadFactory, handler);


        //给线程池分配任务
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
                poolExecutor.submit(() -> {
                    try {
                        for (int i = 0; i < maximumPoolSize; i++) {
                            System.out.println(Thread.currentThread().getName() + "在执行");
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
