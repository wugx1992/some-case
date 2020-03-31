package indi.gxwu.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: gx.wu
 * @Date: 2020/3/19 16:12
 * @Description: code something to describe this module what it is
 */
public class ConditionTest {

    public static ReentrantLock lock = new ReentrantLock();
    public static Condition conditionA = lock.newCondition();
    public static Condition conditionB = lock.newCondition();
    public static Condition conditionC = lock.newCondition();

    public static void main(String[] args) {
        ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB();
        ThreadC threadC = new ThreadC();

        threadA.start();//（1）
        threadB.start();//（2）
        threadC.start();//（3）

    }

}
