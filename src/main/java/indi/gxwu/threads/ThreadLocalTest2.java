package indi.gxwu.threads;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: gx.wu
 * @Date: 2020/3/27 10:33
 * @Description: 相对于 threadLocal，测试 static 静态变量的效果
 */
public class ThreadLocalTest2 {


    static class ThreadLocalId{
        private static final AtomicInteger nextId = new AtomicInteger(0);
        private static final ThreadLocal<Integer> threadIds = new ThreadLocal<Integer>(){
            @Override
            protected Integer initialValue(){
                System.out.println("time: "+System.currentTimeMillis()+ ", "+Thread.currentThread().getName()+", initialValue");
                return nextId.getAndIncrement();
            }
        };
        public static int get(){
            return threadIds.get();
        }
        public static void remove(){
            threadIds.remove();
        }
    }


    public static void startNewThread(){
        Thread thread = new Thread(getRunnable());
        thread.start();
    }


    public static Runnable getRunnable(){
        return new Runnable() {
            @Override
            public synchronized void run() {
                try {
                    for (int i = 0; i < 3; i++) {
                        System.out.println("time: "+System.currentTimeMillis()+", "+Thread.currentThread().getName()+ ", i: " + i + ", localValue: " + ThreadLocalId.get());
//                        this.wait(100);
                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ThreadLocalId.remove();
                }
            }
        };
    }

    public static void main(String[] args) {
//        startNewThread();
//        startNewThread();
//        startNewThread();
//        startNewThread();

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 3, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        threadPool.execute(getRunnable());
        threadPool.execute(getRunnable());
        threadPool.execute(getRunnable());
        threadPool.execute(getRunnable());
        threadPool.execute(getRunnable());
        threadPool.execute(getRunnable());

        threadPool.shutdown();
    }
}
