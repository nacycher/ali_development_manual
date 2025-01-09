package org.lb.songshan_17que.verify17que;

import java.math.BigDecimal;

/**
 * 02 为什么禁止使用 double 直接构造 BigDecimal？
 */
public class A02BigDecimalByDouble {
    public static void main(String[] args) {
        /**
         * 使用double直接相加存在精度丢失的风险。
         * 0.1转换为2进制是无线循环数，所以在计算机中是无法准确表示的
         * 补充十进制小数转二进制转换方法 ：*2取整，记录余数，再将余数乘以2，再取整，直到小数部分为0
         *
         * double 和 float 使用了IEEE 754 标准，该标准使用了双精度浮点数和单精度浮点数来表示小数
         * double的0.1只是一个近似值，并不是一个精确的值，所以在进行计算时会出现精度丢失的问题
         */

        double a = 0.1;
        double b = 0.2;
        System.out.println(a + b); // 输出 0.30000000000000004

        // 使用double创建BigDecimal同样存在精度丢失问题
        BigDecimal c = new BigDecimal(0.1D);
        // 推荐使用BigDecimal的valueOf方法(内含toString方法) 或者 BigDecimal的String构造方法
        BigDecimal d = BigDecimal.valueOf(0.1D);

    }
}
