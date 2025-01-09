package org.lb.songshan_17que.verify17que;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 04 为什么要求日期格式化时必须有使用 y 表示年，而不能用 Y ？
 */
public class A04UsingyyyyInsteadYYYY {
    /**
     * YYYY表示的是Week Year，而不是Year，Week Year是指包含这一周的一年，
     * 如果2020年的第1周跨年，那么这一周就属于2021年，所以YYYY表示的是2021年
     */
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Sun Dec 29 00:00:00 HKT 2024
        System.out.println(dateFormat1.parse("2025-01-01 00:00:00"));
        // Wed Jan 01 00:00:00 HKT 2025
        System.out.println(dateFormat2.parse("2025-01-01 00:00:00"));
    }

}
