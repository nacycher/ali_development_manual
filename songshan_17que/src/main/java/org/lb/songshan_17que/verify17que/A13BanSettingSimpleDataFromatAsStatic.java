package org.lb.songshan_17que.verify17que;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

// 13 为什么禁止把 SimpleDateFormat 定义成 static 变量？
public class A13BanSettingSimpleDataFromatAsStatic {
    /**
     * SimpleDataFormat是线程不安全的
     * 其转换方法内，使用到了成员变量Calendar，Calendar.setTime()方法，可以被多个线程同时访问修改
     */


    /**
     * 定义一个全局变量，线程不安全的SIMPLE_DATE_FORMAT
      */
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Test
    public void repeatStaticSimpleDataFormat() {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
                10,
                5,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(10),
                new ThreadFactoryBuilder().setNameFormat("SimpleDataFormatThread-%d").build());
        Set<String> dateSet = Collections.synchronizedSet(new HashSet<String>());

        // 100个任务，交给线程池，并发的调用SIMPLE_DATE_FORMAT转换时间，并将时间放到Set集合中
        for (int i = 0; i < 100; i++) {
            Calendar calendar = Calendar.getInstance();
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                calendar.add(Calendar.DATE, finalI);
                String format = SIMPLE_DATE_FORMAT.format(calendar.getTime());
                dateSet.add(format);
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await();
            // dateSet应该存100个不同的时间，
            // 但是因为是多线程，线程不安全的去转换时间，导致多个线程转换了同一个时间
            System.out.println(dateSet.size());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 可行的解决方案：
     * 1.给共享变量SIMPLE_DATE_FORMAT加锁
     * 2.使用线程安全的日期转换工具类，DateTimeFormater
     * 3.SIMPLE_DATE_FORMAT 定义ThreadLocal线程私有变量
     * 4.每个线程自己创建一个SimpleDateFormat，局部变量
     */

    private static ThreadLocal<SimpleDateFormat> THREAD_LOCAL_SIMPLE_DATE_FORMAT = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    @Test
    public void threadLocalSimpleDataFormat() {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
                10,
                5,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(10),
                new ThreadFactoryBuilder().setNameFormat("SimpleDataFormatThread-%d").build());
        Set<String> dateSet = Collections.synchronizedSet(new HashSet<String>());

        // 100个任务，交给线程池，并发的调用SIMPLE_DATE_FORMAT转换时间，并将时间放到Set集合中
        for (int i = 0; i < 100; i++) {
            Calendar calendar = Calendar.getInstance();
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                calendar.add(Calendar.DATE, finalI);
                String format = THREAD_LOCAL_SIMPLE_DATE_FORMAT.get().format(calendar.getTime());
                dateSet.add(format);
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await();
            // dateSet应该存100个不同的时间，
            // 因为是多线程，且使用了ThreadLocal定义SimpleDataFormat，所以日期转换是线程安全的
            System.out.println(dateSet.size());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void syncLockSimpleDataFormat() {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
                10,
                5,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNameFormat("SimpleDataFormatThread-%d").build());
        Set<String> dateSet = Collections.synchronizedSet(new HashSet<String>());

        // 100个任务，交给线程池，并发的调用SIMPLE_DATE_FORMAT转换时间，并将时间放到Set集合中
        for (int i = 0; i < 100; i++) {
            Calendar calendar = Calendar.getInstance();
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                synchronized (SIMPLE_DATE_FORMAT) {
                    calendar.add(Calendar.DATE, finalI);
                    String format = SIMPLE_DATE_FORMAT.format(calendar.getTime());
                    dateSet.add(format);
                    countDownLatch.countDown();
                }
            });
        }

        try {
            countDownLatch.await();
            // dateSet应该存100个不同的时间，
            // 但是因为是多线程，线程不安全的去转换时间，导致多个线程转换了同一个时间
            System.out.println(dateSet.size());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Test
    public void dateTimeFormatterTest() {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
                10,
                5,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNameFormat("SimpleDataFormatThread-%d").build());
        Set<String> dateSet = Collections.synchronizedSet(new HashSet<String>());

        // 100个任务，交给线程池，并发的调用SIMPLE_DATE_FORMAT转换时间，并将时间放到Set集合中
        for (int i = 0; i < 100; i++) {
            Calendar calendar = Calendar.getInstance();
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                calendar.add(Calendar.DATE, finalI);
                String format = LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault()).format(DATE_TIME_FORMATTER);
                dateSet.add(format);
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await();
            // dateSet应该存100个不同的时间，
            // 但是因为是多线程，线程不安全的去转换时间，导致多个线程转换了同一个时间
            System.out.println(dateSet.size());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
