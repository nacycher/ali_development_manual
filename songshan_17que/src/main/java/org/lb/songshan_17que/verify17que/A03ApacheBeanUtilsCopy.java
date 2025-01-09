package org.lb.songshan_17que.verify17que;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Assert;
import org.junit.Test;
import org.lb.songshan_17que.entity.User;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 03 为什么禁止使用 Apache Beanutils 进行属性的 copy ？
 */
public class A03ApacheBeanUtilsCopy {
    /**
     * 使用apache的beanUtils.copyProperties方法进行属性copy，会存在性能问题
     * 其做了类型兼容处理、类型转换处理、属性值为空的处理等
     */

    // 模拟数据
    public List<User> initUsers() {
        List result = new ArrayList();
        for (int i = 0; i < 1000000; i++) {
            User user = new User();
            user.setId((long) i);
            user.setName("name" + i);
            user.setAge(i);
            user.setAddress("address" + i);
            user.setPhone("phone" + i);
            user.setEmail("email" + i);
            user.setIdCard("IdCard" + i);
            user.setBankPhone("BankPhone" + i);

            result.add(user);
        }
        return  result;
    }

    @Test
    public void TestApacheBeanUtils() throws InvocationTargetException, IllegalAccessException {
        List<User> initUsers = initUsers();
        List<User> targetUsers = new ArrayList<>();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (User initUser : initUsers) {
            User targetUser = new User();
            BeanUtils.copyProperties(targetUser, initUser);
            targetUsers.add(targetUser);
        }
        stopWatch.stop();
        // 平均约8100-8500ms
        System.out.println("耗时：" + stopWatch.getTotalTimeMillis() + "ms");
    }

    @Test
    public void TestSpringBeanUtils() throws InvocationTargetException, IllegalAccessException {
        List<User> initUsers = initUsers();
        List<User> targetUsers = new ArrayList<>();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (User initUser : initUsers) {
            User targetUser = new User();
            org.springframework.beans.BeanUtils.copyProperties(targetUser, initUser);
            targetUsers.add(targetUser);
        }
        stopWatch.stop();
        // 平均约1100-1500ms
        System.out.println("耗时：" + stopWatch.getTotalTimeMillis() + "ms");
    }
}
