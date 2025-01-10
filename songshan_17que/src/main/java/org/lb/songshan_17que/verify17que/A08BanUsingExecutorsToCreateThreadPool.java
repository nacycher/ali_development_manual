package org.lb.songshan_17que.verify17que;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 08 禁止使用 Executors 去创建线程池
 */
public class A08BanUsingExecutorsToCreateThreadPool {
    // Executors 创建的线程池 参数(队列长度、最大线程数)是没有边界的，可能会导致OOM
    @Test
    public void initThreadPoolByExecutors() {
        // 以下两个的队列最大长度是Integer.MAX_VALUE,
        // 使用了LinkedBlockingQueue默认边界最大值是Integer.MAX_VALUE
        Executors.newFixedThreadPool(5);
        Executors.newSingleThreadExecutor();
        // 以下两个的最大线程数，默认是Integer.MAX_VALUE
        Executors.newCachedThreadPool();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

        // 测试：OOM,可以设置jvm参数-Xmx800m -Xms800m
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            scheduledExecutorService.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // do nothing
                }
            });
        }


    }
}
