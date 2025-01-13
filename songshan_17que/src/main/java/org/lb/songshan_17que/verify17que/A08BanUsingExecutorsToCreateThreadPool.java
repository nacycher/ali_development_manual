package org.lb.songshan_17que.verify17que;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * 08 禁止使用 Executors 去创建线程池
 */
@Slf4j
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
        Executors.newScheduledThreadPool(5);

        // 测试：OOM error,（可以设置jvm参数-Xmx800m -Xms800m）
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            scheduledExecutorService.execute(() -> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    // do nothing
                }
            });

        }
    }

    // 使用ThreadPoolExecutor创建线程池，自定义线程池参数
    @Test
    public void initThreadPoolByThreadPoolExecutor() {
        // 这里自定义线程池参数，
        // 使用了ArrayBlockQueue定义了队列长度，当任务大于长度时(实际还和线程池执行逻辑有关，这里略过)抛出异常
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                5,
                10,
                2L,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(10)
        );
        try {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                threadPoolExecutor.execute(() -> {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        // do nothing
                    }
                });
            }
        } catch (Exception e) {
            System.out.println("线程池异常，拒绝执行" + e);
        }

    }

    // 使用其他开源类库创建线程池
    @Test
    public void initThreadPoolByGuavaThreadPoolBuilder () {
        // guava的ThreadFactoryBuilder,可以定义线程名，更好的排查问题
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("guava-thread-%d")
                .build();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                5,
                10,
                2L,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(10),
                threadFactory
        );
        try {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                threadPoolExecutor.execute(() -> {
                    try {
                        log.info(Thread.currentThread().getName());
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        // do nothing
                    }
                });
            }
        } catch (Exception e) {
            log.info("线程池异常，拒绝执行 {0}", e);
        }

    }




}
