package indi.gxwu.threads;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: gx.wu
 * @Date: 2020/4/7 16:33
 * @Description: code something to describe this module what it is
 */
public class ReentrantLockTest {

    /**
     * lockInterruptibly 使用
     */
    public static class Runnable1 implements Runnable {
        Lock firstLock;
        Lock secondLock;
        public Runnable1(Lock first, Lock second){
            this.firstLock = first;
            this.secondLock = second;
        }

        @Override
        public void run() {
            try {
                System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" try get lock "+firstLock);
                firstLock.lockInterruptibly();
                TimeUnit.MILLISECONDS.sleep(10);
                System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" try get lock "+secondLock);
                secondLock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                firstLock.unlock();
                secondLock.unlock();
                System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" 获取到资源结束");
            }
        }
    }

    /**
     * tryLock 使用
     */
    public static class Runnable2 implements Runnable {
        Lock firstLock;
        Lock secondLock;

        public Runnable2(Lock first, Lock second){
            firstLock = first;
            secondLock = second;
        }

        @Override
        public void run() {
            try {
                System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" try get lock "+firstLock);
                if(!firstLock.tryLock(10, TimeUnit.MILLISECONDS)){
                    System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" get  lock fail "+firstLock);
                    TimeUnit.MILLISECONDS.sleep(100);
                }
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" try get lock "+secondLock);
                if(!secondLock.tryLock(10, TimeUnit.MILLISECONDS)){
                    System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" get lock fail "+secondLock);
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                firstLock.unlock();
                secondLock.unlock();
                System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" 获取到资源结束");
            }
        }
    }

    /**
     * 测试公平锁与非公平锁
     */
    public static class Runnable3 implements Runnable {
        private Lock lock;
        public Runnable3(Lock lock){
            this.lock = lock;
        }
        @Override
        public void run(){
            try{
                lock.lock();
                System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()
                        +" the lock is fair: "+((ReentrantLock)lock).isFair()+" queueLength: "+((ReentrantLock)lock).getQueueLength());
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("----------------- 使用 lockInterruptibly 支持主动打断线程，进而停止死锁 --------------------");
        TimeUnit.MILLISECONDS.sleep(100);

        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();

        Thread thread1 = new Thread(new Runnable1(lock1, lock2));
        Thread thread2 = new Thread(new Runnable1(lock2, lock1));
        thread1.start();
        thread2.start();

        TimeUnit.MILLISECONDS.sleep(1000);
        System.out.println("----------------- 打断线程，停止死锁 -----------------");
        thread1.interrupt();

        TimeUnit.MILLISECONDS.sleep(2000);
        System.out.println("----------------- 使用tryLock(long, TimeUnit) 尝试 long 时间片段获取锁，否则返回获取锁失败。无参数表示立即获取 --------------------");
        TimeUnit.MILLISECONDS.sleep(100);


        Lock lock3 = new ReentrantLock();
        Lock lock4 = new ReentrantLock();

        Thread thread3 = new Thread(new Runnable2(lock3, lock4));
        Thread thread4 = new Thread(new Runnable2(lock4, lock3));
        thread3.start();
        thread4.start();

        TimeUnit.MILLISECONDS.sleep(2000);
        System.out.println("----------------- 公平锁与非公平锁的比较 --------------------");
        System.out.println("----------------- 使用 公平锁 --------------------");
        TimeUnit.MILLISECONDS.sleep(100);
        Lock lock5 = new ReentrantLock(true);
        Thread thread5 = new Thread(new Runnable3(lock5));
        Thread thread6 = new Thread(new Runnable3(lock5));
        Thread thread7 = new Thread(new Runnable3(lock5));
        Thread thread8 = new Thread(new Runnable3(lock5));
        Thread thread9 = new Thread(new Runnable3(lock5));
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();

        TimeUnit.MILLISECONDS.sleep(2000);
        System.out.println("----------------- 使用 非公平锁 --------------------");
        TimeUnit.MILLISECONDS.sleep(100);
        Lock lock10 = new ReentrantLock();
        Thread thread10 = new Thread(new Runnable3(lock10));
        Thread thread11 = new Thread(new Runnable3(lock10));
        Thread thread12 = new Thread(new Runnable3(lock10));
        Thread thread13 = new Thread(new Runnable3(lock10));
        Thread thread14 = new Thread(new Runnable3(lock10));
        thread10.start();
        thread11.start();
        thread12.start();
        thread13.start();
        thread14.start();


    }
}
