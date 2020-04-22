package indi.gxwu.threads;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author: gx.wu
 * @Date: 2020/4/22 11:55
 * @Description: code something to describe this module what it is
 */
public class CountDownLatchTest {



    public static class RunnableObj implements Runnable {
        CountDownLatch latch;

        public RunnableObj(CountDownLatch latch){
            this.latch = latch;
        }

        @Override
        public void run(){
            System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" 随机休眠");
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" 当前计数："+latch.getCount()+" 计数器减一");
            latch.countDown();

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" 线程结束，CountDownLatch计数已清零");

        }
    }

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(5);

        for(int i=0;i<5;i++){
            Thread thread = new Thread(new RunnableObj(latch));
            thread.start();
        }
    }

}
