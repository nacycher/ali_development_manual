package org.lb.songshan_17que.verify17que;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 05 三元运算符的类型必须相同
 */
public class A05TripleComputeUsingSameType {
    /**
     * java存在自动拆装箱机制
     * 三元运算符可能会自动拆装箱，如果是null自动装箱，那么就会出现空指针异常
     */
    public static void main(String[] args) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("key1", false);

        // 两边类型相同不存在自动拆装箱，不会抛出空指针异常
        Boolean a = Objects.nonNull(map) ? map.get("key2") : Boolean.FALSE;

        // map.get("key2")返回null，拆箱为boolean类型，抛出空指针异常
        Boolean b = Objects.nonNull(map) ? map.get("key2") : false;
    }

}
