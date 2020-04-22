package indi.gxwu.threads;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: gx.wu
 * @Date: 2020/4/22 12:26
 * @Description: code something to describe this module what it is
 */
public class ReentrantReadWriteLockTest {
    static int writeCount;
    static int result;

    public static class RunnableRead implements Runnable{
        private CountDownLatch readLatch;
        private ReentrantReadWriteLock.ReadLock readLock;

        public RunnableRead(CountDownLatch readLatch, ReentrantReadWriteLock.ReadLock readLock){
            this.readLatch = readLatch;
            this.readLock = readLock;
        }

        @Override
        public void run(){
            while (true){
                if(writeCount>=5){
                    break;
                }

                try {
                    readLock.lock();
                    System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" countDown: "+readLatch.getCount()+" 【Read】 result: "+result);
                    readLatch.countDown();

                }finally {
                    readLock.unlock();
                }
            }
        }
    }


    public static class RunnableWrite implements Runnable{
        private CountDownLatch readLatch;
        private ReentrantReadWriteLock.WriteLock writeLock;

        public RunnableWrite(CountDownLatch readLatch, ReentrantReadWriteLock.WriteLock writeLock){
            this.readLatch = readLatch;
            this.writeLock = writeLock;
        }

        @Override
        public void run(){
            System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" writeCount: "+writeCount+" 【wait for Write】 result: "+result);
            try {
                readLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (true){
                try {
                    writeLock.lock();
                    if(writeCount>=5){
                        break;
                    }
                    int r = result;
                    result = r+1;
                    System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" writeCount: "+writeCount+" 【Write】 result: "+result);
                    writeCount++;
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    writeLock.unlock();
                }
            }
        }
    }


    public static void main(String[] args) {

        System.out.println("【ReentrantReadWriteLock 读读共享、读写互斥、写写互斥】");
        CountDownLatch readLatch = new CountDownLatch(8);
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        for(int i=0;i<3;i++){
            Thread thread = new Thread(new RunnableWrite(readLatch, rwLock.writeLock()));
            thread.start();
        }

        for(int i=0;i<3;i++){
            Thread thread = new Thread(new RunnableRead(readLatch, rwLock.readLock()));
            thread.start();
        }


    }
}
