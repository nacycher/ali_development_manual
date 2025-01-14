package org.lb.songshan_17que.verify17que;

import org.junit.Test;

import java.util.*;

/**
 * 11 为什么禁止在 foreach 循环里进行元素的 remove/add 操作？
 */
public class A11BanRemovingAddingElementInForeachLoop {
    /**
     * foreach，增强for循环，是java提供的语法糖，在编译时会编译为iterator+while的循环
     *
     * java数组中存在quick-fail机制，判断多线程环境中是否存在并发修改的情况
     * 使用list.remove/add方法并不会去修改expectModcount(这时iterator自己内部的成员变量)，只会改modCount,导致两个count不同
      */

    /**
     * 解决方案：
     * 1，使用普通fori循环，
     * 2.使用stream流过滤数据生成新的数组，
     * 3.直接使用iterator便利数据
     */

    /**
     * 普通fori循环
     */
    @Test
    public void removeElementInNormalForLoop() {
        List<String> userNames = initArrayList();
        for (int i = 0; i < userNames.size(); i++) {
            if (userNames.get(i).equals("Hollis")) {
                userNames.remove(i);
            }
        }
        System.out.println(userNames);
    }


    /**
     * 该方法中：
     * 无论是add操作或者remove操作都会报异常：java.util.ConcurrentModificationException
     */
    @Test
    public void removeElementInEnhanceForLoop() {
        List<String> userNames = initArrayList();
        for (String userName : userNames) {
            if (userName.equals("Hollis")) {
//                userNames.remove(userName);
                userNames.add("hello");
            }
        }
        System.out.println(userNames);
    }

    public static List<String> initArrayList() {
        return new ArrayList<>(Arrays.asList("Hollis", "hollis", "HollisChuang", "H"));
    }

}
