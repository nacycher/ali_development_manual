package org.lb.songshan_17que.verify17que;

import org.junit.Test;

import java.util.*;

/**
 * 11 为什么禁止在 foreach 循环里进行元素的 remove/add 操作？
 */
public class A11BanRemovingAddingElementInForeachLoop {


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
