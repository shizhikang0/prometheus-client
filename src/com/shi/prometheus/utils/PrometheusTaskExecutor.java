package com.shi.prometheus.utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/12/8
 */
public class PrometheusTaskExecutor {

    private static AtomicInteger count = new AtomicInteger(0);
    private static int corePoolSize = 4;
    private static int maximumPoolSize = 8;
    private static long keepAliveTime = 10;
    private static TimeUnit timeUnit = TimeUnit.SECONDS;

    private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(8);

    private static ThreadFactory myThreadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            String threadName = "线程" + count.addAndGet(1);
            Thread t = new Thread(runnable, threadName);
            return t;
        }
    };
    //定义任务拒绝执行的策略
    private static RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            // 记录异常
            // 报警处理等
            int iActiveCount = executor.getActiveCount();
            int iRemainQueue = executor.getQueue().remainingCapacity();
            int iexsitQueue = executor.getQueue().size();
            System.out.println("Warn:当前运行的线程数:"+String.valueOf(iActiveCount) +
                    ",当前队列剩余数:" + iRemainQueue +
                    ",当前队列排队的任务数:" + iexsitQueue);
        }
    };

    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            corePoolSize,
            maximumPoolSize,
            keepAliveTime,
            timeUnit,
            workQueue,
            myThreadFactory,
            rejectedExecutionHandler
    );

    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
