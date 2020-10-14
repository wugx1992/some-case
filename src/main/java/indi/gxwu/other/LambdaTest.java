package indi.gxwu.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author: gx.wu
 * @Date: 2020/4/23 10:23
 * @Description: code something to describe this module what it is
 */
public class LambdaTest {


    @FunctionalInterface
    interface MathOperation{
        int operate(int x, int y);
    }


    public static int testOperation(int x, int y, MathOperation operation){
        return operation.operate(x, y);
    }

    /**
     * lambda 基本用法
     */
    public static void test1(){
        /**
         * Lambda 允许把函数作为一个方法的参数（函数作为参数传递进方法中）
         */
        MathOperation addition = (x,y) -> {return x+y;};
        MathOperation subtraction = (a,b) -> a-b;
        MathOperation multiplication = (int a, int b)->a*b;
        MathOperation division = (a, b)-> a/b;

        System.out.println(addition.operate(33,11));
        System.out.println(testOperation(33, 11, addition));
        System.out.println(testOperation(33, 11, subtraction));
        System.out.println(testOperation(33, 11, multiplication));
        System.out.println(testOperation(33, 11, division));
    }

    /**
     * lambda 与 Runnable 接口
     */
    public static void test2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" hello world!");
            }
        }).start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+" hello world!");
        }).start();
    }

    public static void test3(){
        List<Integer> list = Arrays.asList(2,4,1,3,5);
        System.out.println(list);
        Collections.sort(list, (a, b)->{
            if(a>b){
                return -1;
            }else {
                return 1;
            }
        });
        System.out.println(list);
        list.forEach((x)->{
            System.out.println("【"+x+"】");
        });
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

}
