package indi.gxwu.threads;

import java.lang.ref.WeakReference;

/**
 * @Author: gx.wu
 * @Date: 2020/4/3 12:04
 * @Description: ThreadLocal 弱引用使用不当，内存泄露
 */
public class ThreadLocalLeak {


    public static void unsuccessfulGC(){
        String str = new String("Test ThreadLocal memory leak!");
        WeakReference<String> weakReference = new WeakReference<String>(str);
        System.gc();
        if(weakReference.get()==null){
            System.out.println("1 weakReference is gc!");
        }else{
            System.out.println("1 memory gc unsuccessful gc, "+weakReference.get());
        }
    }


    public static void successfulGC(){
        WeakReference<String> weakReference = new WeakReference<String>(new String("Test ThreadLocal memory leak!"));
        System.gc();
        if(weakReference.get()==null){
            System.out.println("2 weakReference is gc!");
        }else{
            System.out.println("2 memory gc unsuccessful gc, "+weakReference.get());
        }
    }


    public static void main(String[] args) {
        unsuccessfulGC();
        System.out.println("-------------------");
        successfulGC();
    }
}


