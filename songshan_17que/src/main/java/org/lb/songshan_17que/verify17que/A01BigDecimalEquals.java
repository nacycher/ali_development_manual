package org.lb.songshan_17que.verify17que;

import java.math.BigDecimal;

/**
 * 为什么禁止使用BigDecimal的equals方法做等值比较？
 */
public class A01BigDecimalEquals {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("1.0");
        BigDecimal b = new BigDecimal("1.00");

        // BigDecimal的equals方法是比较值和精度的
        System.out.println(a.equals(b));
        System.out.println(a.compareTo(b) == 0);
    }
}
