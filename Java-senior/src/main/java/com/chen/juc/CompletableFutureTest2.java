package com.chen.juc;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * @author Robert V
 * @create 2022-10-10-下午7:12
 */
public class CompletableFutureTest2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int corePoolSize = 5;
        int maximumPoolSize = 7;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                unit, workQueue, threadFactory, handler);

        //创建Supplier类型对象，封装任务内容
        Supplier<String> supplier = () -> {
            System.out.println(Thread.currentThread().getName() + "is working");
            return "task result";
        };

        //将异步任务提交到自己指定的线程池
        CompletableFuture<String> future = CompletableFuture.supplyAsync(supplier, poolExecutor);

        //调用get获取任务结果
        String result = future.get();
        System.out.println("result = " + result);

        //设定异步回调方法
        future.whenCompleteAsync((String taskResult, Throwable throwable) -> {
            System.out.println(Thread.currentThread().getName() + ", taskResult = " + taskResult);
            System.out.println(Thread.currentThread().getName() + ", throwable = " + throwable);
        });


    }
}
