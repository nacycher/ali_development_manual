package org.lb.songshan_17que.verify17que;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.util.StopWatch;

/**
 * 10 为什么不建议在for循环中使用“+”进行字符串拼接？
 */
public class A10UnSuggestUsingPlusStringInForLoop {
    // String类维护了一个不可变的char数组，
    // 每次对String操作都会生成一个新的String对象，占用更多的内存空间

    // 反例，在for循环中使用“+”进行字符串拼接
    @Test
    public void stringPushInForLoop() {
        // 这里设置JVM 3M内存 -Xmx3m -Xms3m
        String str = "";
        for (int i = 0; i < 100000; i++) {
            str = "string" + i;
        }
        System.out.println(str);
    }

    // 正例，单线程环境中使用stringbuilder拼接
    @Test
    public void stringPushByBuilderInForLoop() {
        // 这里设置JVM 3M内存 -Xmx3m -Xms3m
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            stringBuilder.append("string").append(i);
        }
    }

    /**
     * String各种拼接方法效率对比
     * 结论：stringBuilder<stringBuffer<contact<+<stringUtils.join
     */
    @Test
    public void stringAppendCostTime0() {
        StopWatch stopWatch = new StopWatch();
        StringBuilder stringBuilder = new StringBuilder();
        // string builder
        stopWatch.start();
        for (int i = 0; i < 100000; i++) {
            stringBuilder.append(i);
        }
        stopWatch.stop();
        System.out.println("String Builder time ms :" + stopWatch.getTotalTimeMillis());
    }

    @Test
    public void stringAppendCostTime1() {
        StopWatch stopWatch = new StopWatch();
        StringBuffer stringBuffer = new StringBuffer();

        // string buffer
        stopWatch.start();
        for (int i = 0; i < 100000; i++) {
            stringBuffer.append(i);
        }
        stopWatch.stop();
        System.out.println("String buffer time ms :" + stopWatch.getTotalTimeMillis());
    }
    @Test
    public void stringAppendCostTime3() {
        StopWatch stopWatch = new StopWatch();

        // contact
        stopWatch.start();
        String str = "";
        for (int i = 0; i < 100000; i++) {
            str = str.concat(i + "");
        }
        stopWatch.stop();
        System.out.println("contact time ms :" + stopWatch.getTotalTimeMillis());
    }
    @Test
    public void stringAppendCostTime4() {
        StopWatch stopWatch = new StopWatch();

        // +
        stopWatch.start();
        String str2 = "";
        for (int i = 0; i < 100000; i++) {
            str2 = str2 + i;
        }
        stopWatch.stop();
        System.out.println("+ time ms :" + stopWatch.getTotalTimeMillis());
    }
    @Test
    public void stringAppendCostTime5() {
        StopWatch stopWatch = new StopWatch();

        // stringUtils.join
        stopWatch.start();
        String str3 = "123";
        for (int i = 0; i < 100000; i++) {
            str3 = StringUtils.join(str3, i);
        }
        stopWatch.stop();
        System.out.println("stringUtils.join time ms :" + stopWatch.getTotalTimeMillis());
    }



}
