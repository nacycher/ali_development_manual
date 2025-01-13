package org.lb.songshan_17que.verify17que;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 09 为什么要求谨慎使用 ArrayList 中的 subList 方法？
 */
@Slf4j
public class A09UnSuggestUsingArraysSubList {
    // ArrayList中的subList方法返回的是Arrays的内部类SubList(源码又称其为view视图)
    // subList直接使用了ArrayList中的成员变量

    @Test
    public void subListToArrayList() {
        // 类型转换错误
        // ArrayList subList = initList().subList(0,3);

        ArrayList arrayList = initList();
        List subList = arrayList.subList(0, 3);
        log.info("subList is {}", subList);
        log.info("arrayList is {}", arrayList);

        // 改变subList中的元素，arrayList中的元素也跟着改变，(使用同一对象引用的地址)
        subList.remove(0);
        subList.add(999);
        log.info("subList is {}", subList);
        log.info("arrayList is {}", arrayList);

        // 通过subList创建一个新的List
        ArrayList arrayList1 = (ArrayList) subList.stream().skip(0L).limit(subList.size()).collect(Collectors.toList());
        log.info("通过subList创建一个新的List arrayList1 is {}", arrayList1);
        arrayList1.add(9000);
        log.info("subList is {}", subList);
        log.info("arrayList is {}", arrayList);
        log.info("通过subList创建一个新的List arrayList1 is {}", arrayList1);
        // 改变subList中的元素，arrayList中的元素不会改变，(使用不同对象引用的地址)
    }

    private ArrayList initList() {
        return new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }
}
