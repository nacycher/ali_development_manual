package org.lb.songshan_17que.verify17que;

/**
 * 17 为什么禁止使用 count( 列名 ) 或 count( 常量 ) 来替代 count(*) ？
 */
public class A17UsingCountxInsteadOfCount1 {
    // mysql中count(*) 会统计所有的行数，包括null值的行。
    // count(*)是标准的sql语法，mysql数据库对其做过优化
}
