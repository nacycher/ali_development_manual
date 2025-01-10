package org.lb.songshan_17que.verify17que;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.lb.songshan_17que.entity.User;
import org.springframework.util.StopWatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 07 Java开发手册建议创建 HashMap 时设置初始化容量，但是多少合适呢？
 */
public class A07InitHashMapWithProperSize {
    private static final Integer HASH_MAP_INIT_SIZE = 5000000;

    /**
     * 可能存在初始化传入7，hashmap初始化大小为8，但是当插入第7个元素时，发生扩容
     * 因此可以考虑使用Maps的newHashMapWithExpectedSize方法，在8的基础上又扩充一倍，即hashmap初始化大小为16
     */

    // HashMap 不指定初始化大小 VS 指定大小
    // 处理千w级别数据时有效果, 只处理百万级别数据时，效果不大
    @Test
    public void initHashMapWithSize00 () {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        HashMap<String, User> hashMap = new HashMap<>();
        initMapElement(hashMap);
        stopWatch.stop();
        System.out.println("不指定初始化大小，耗时ms：" + stopWatch.getTotalTimeMillis());
    }
    @Test
    public void initHashMapWithSize01 () {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        HashMap<String, User> hashMap = new HashMap<>(HASH_MAP_INIT_SIZE);
        initMapElement(hashMap);
        stopWatch.stop();
        System.out.println("指定初始化大小，耗时ms：" + stopWatch.getTotalTimeMillis());

    }
    @Test
    public void initHashMapWithSize02 () {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        HashMap<String, User> hashMap = Maps.newHashMapWithExpectedSize(HASH_MAP_INIT_SIZE);
        initMapElement(hashMap);
        stopWatch.stop();
        System.out.println("Maps方法指定初始化大小，耗时ms：" + stopWatch.getTotalTimeMillis());
    }
    // 初始化hashMap取大于等于传入值的最小2的n次方
    @Test
    public void initHashMapWithSize1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HashMap<String, String> map = new HashMap<>(1);
        Class<?> aClass = map.getClass();
        Method capacity = aClass.getDeclaredMethod("capacity");
        capacity.setAccessible(true);
        Object invoke = capacity.invoke(map);
        System.out.println("map init by 1, size is " + invoke); // 1

        HashMap<String, String> map2 = new HashMap<>(7);
        Class<?> aClass2 = map.getClass();
        Method capacity2 = aClass2.getDeclaredMethod("capacity");
        capacity2.setAccessible(true);
        Object invoke2 = capacity.invoke(map2);
        System.out.println("map init by 1, size is " + invoke2); // 8


        HashMap<String, String> map3 = new HashMap<>(9);
        Class<?> aClass3 = map.getClass();
        Method capacity3 = aClass3.getDeclaredMethod("capacity");
        capacity3.setAccessible(true);
        Object invoke3 = capacity.invoke(map3);
        System.out.println("map init by 1, size is " + invoke3); // 16
    }


    // 当创建一个容量为7的hashMap时，capacity是8，当put第7个元素时，发生扩容
    // 因此使用Maps的newHashMapWithExpectedSize方法，在8的基础上又扩充一倍，即capacity是16
    @Test
    public void initHashMapWithSize2() throws Exception {
        HashMap<String, String> map = new HashMap<>(7);
        Class<?> aClass = map.getClass();
        Method capacity = aClass.getDeclaredMethod("capacity");
        capacity.setAccessible(true);
        Object invoke = capacity.invoke(map);
        System.out.println("map init by 1, size is " + invoke);

        map.put("1", "1");
        map.put("2", "1");
        map.put("3", "1");
        map.put("4", "1");
        map.put("5", "1");
        map.put("6", "1");
        map.put("7", "1"); // 发生扩容
        Object invoke2 = capacity.invoke(map);
        System.out.println("map after put 7 element, size is " + invoke2);
    }

    // 使用Maps的newHashMapWithExpectedSize方法创建hashMap
    @Test
    public void initHashMapWithSize3() throws Exception {
        HashMap<String, String> map = Maps.newHashMapWithExpectedSize(7);
        Class<?> aClass = map.getClass();
        Method capacity = aClass.getDeclaredMethod("capacity");
        capacity.setAccessible(true);
        Object invoke = capacity.invoke(map);
        System.out.println("map init by 1, size is " + invoke);

        map.put("1", "1");
        map.put("2", "1");
        map.put("3", "1");
        map.put("4", "1");
        map.put("5", "1");
        map.put("6", "1");
        map.put("7", "1"); // 不会发生扩容
        Object invoke2 = capacity.invoke(map);
        System.out.println("map after put 7 element, size is " + invoke2);
    }

    private static void initMapElement(Map<String, User> hashMap) {
        for (int i = 0; i < HASH_MAP_INIT_SIZE; i++) {
            User user = new User();
            user.setId((long) i);
            user.setName("name" + i);
            user.setAge(i);
            user.setAddress("address" + i);
            user.setPhone("phone" + i);
            user.setEmail("email" + i);
            user.setIdCard("IdCard" + i);
            user.setBankPhone("BankPhone" + i);

            hashMap.put(String.valueOf(i), user);
        }
    }

}
