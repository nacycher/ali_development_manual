package org.lb.songshan_17que.verify17que;

// 12 为什么禁止工程师直接使用日志系统 (Log4j、Logback) 中的 API ？
public class A12BanUsingLogFreamwork {
    /**
     * 这里直接使用日志门面SLF4j, 相当于超类，提供了日志统一的接口
     * 便于后期替换日志框架，比如log4j,logback等
     */
}
