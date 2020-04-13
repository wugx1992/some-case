package indi.gxwu.threads;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: gx.wu
 * @Date: 2020/4/13 12:25
 * @Description: 自旋锁实现
 */
public class SpinLock implements Lock {

    private AtomicReference<Thread> owner = new AtomicReference<Thread>();
    static int COUNT = 0;

    /**
     * 加锁
     */
    @Override
    public void lock() {
        Thread current = Thread.currentThread();
        /**
         * 自旋CAS
         */
        while (!owner.compareAndSet(null, current)) {
        }
    }

    /**
     * 释放锁
     */
    @Override
    public void unlock() {
        Thread current = Thread.currentThread();
        owner.compareAndSet(current, null);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }


    public static class Runnable1 implements Runnable {
        public Runnable1() {
        }

        @Override
        public void run() {
            int c = COUNT;
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            COUNT = c + 1;
            System.out.println(Thread.currentThread().getName() + " " + COUNT);
        }
    }


    public static class Runnable2 implements Runnable {
        private Lock lock;

        public Runnable2(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {

                lock.lock();
                int c = COUNT;
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(10));
                COUNT = c + 1;
                System.out.println(Thread.currentThread().getName() + " " + COUNT);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Lock lock = new SpinLock();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 10; i++) {
            executor.execute(new Runnable1());
        }

        TimeUnit.MILLISECONDS.sleep(2 * 1000);
        System.out.println("-------------------------");

        for (int i = 0; i < 10; i++) {
            executor.execute(new Runnable2(lock));
        }
        executor.shutdown();
    }

}
