package com.chen;

import java.util.concurrent.*;

/**
 * @author Robert V
 * @create 2022-10-10-下午6:43
 */
public class CompletableFutureTest {
    public static void main(String[] args) {
        int corePoolSize = 5;
        int maximumPoolSize = 7;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                unit, workQueue, threadFactory, handler);

        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + "在工作");
        };

        //将异步任务提交到自己指定的线程池
        CompletableFuture.runAsync(runnable, poolExecutor);
    }
}
