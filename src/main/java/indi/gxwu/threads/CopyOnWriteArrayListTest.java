package indi.gxwu.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: gx.wu
 * @Date: 2020/3/27 14:42
 * @Description: code something to describe this module what it is
 */
public class CopyOnWriteArrayListTest {

    public static List<String> LIST = new CopyOnWriteArrayList();

    static {
        LIST.add("a");
        LIST.add("b");
        LIST.add("c");
    }

    public static Runnable getReadHandle(){
        return new Runnable() {
            @Override
            public void run() {
                for(String value : LIST){
                    System.out.println(Thread.currentThread().getName()+", read , v="+value+", hashCode="+LIST.hashCode());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public static Runnable getWriteHandle(){
        return new Runnable() {
            @Override
            public void run() {
                for(int i=1; i<4; i++){
                    LIST.add(String.valueOf(i));
                    System.out.println(Thread.currentThread().getName()+", write, v="+i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public static Runnable getWriteHandle2(){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<String> newValues = new ArrayList<String>();
                newValues.add("A");
                newValues.add("B");
                newValues.add("C");
                LIST.clear();
                LIST.addAll(newValues);
                System.out.println(Thread.currentThread().getName()+", write, v="+newValues);

            }
        };
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(getReadHandle());
        Thread thread2 = new Thread(getWriteHandle());
        Thread thread3 = new Thread(getReadHandle());
        thread1.start();
        thread2.start();

        Thread.sleep(2*1000);
        System.out.println("---------");
        thread3.start();

        Thread.sleep(2*1000);
        System.out.println("################");
        Thread thread4 = new Thread(getReadHandle());
        Thread thread5 = new Thread(getWriteHandle2());
        Thread thread6 = new Thread(getReadHandle());
        thread4.start();
        thread5.start();

        Thread.sleep(2*1000);
        System.out.println("---------");
        thread6.start();

    }
}
