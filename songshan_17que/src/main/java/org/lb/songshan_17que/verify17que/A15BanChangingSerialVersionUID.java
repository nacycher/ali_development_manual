package org.lb.songshan_17que.verify17que;

/**
 * 15 为什么禁止开发人员修改 serialVersionUID 字段的值？
 */
public class A15BanChangingSerialVersionUID {
    /**
     * serialVersionUID是反序列化和序列化时的版本号，
     * 在反序列化时会比较serialVersionUID是否一致，不一致会抛出异常，反序列化失败
     */

}
