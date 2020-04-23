package indi.gxwu.threads;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author: gx.wu
 * @Date: 2020/4/22 12:09
 * @Description: code something to describe this module what it is
 */
public class SemaphoreTest {

    public static class RunnableObj implements Runnable{
        private Semaphore semaphore;

        public RunnableObj(Semaphore semaphore){
            this.semaphore = semaphore;
        }

        @Override
        public void run(){
            try {
                System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" 准备获取信号量...");
                semaphore.acquire(2);
                System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" 成功获取信号量！随机睡眠...");
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(3000));
                System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" 准备释放信号量...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release(2);
            }
        }
    }


    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(4);
        for(int i=0;i<5;i++){
            Thread thread = new Thread(new RunnableObj(semaphore));
            thread.start();
        }
    }
}
