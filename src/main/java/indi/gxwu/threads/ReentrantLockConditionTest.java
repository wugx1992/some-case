package indi.gxwu.threads;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: gx.wu
 * @Date: 2020/4/10 14:54
 * @Description: code something to describe this module what it is
 */
public class ReentrantLockConditionTest {


    public static void main(String[] args) {
        NumberEntity number = new NumberEntity(0);
        Thread thread1 = new Thread(new OddRunner(number), "ODD-THREAD ");
        Thread thread2 = new Thread(new EvenRunner(number), "EVEN-THREAD");
        thread1.start();
        thread2.start();
    }


    /**
     * 递增计数对象
     */
    public static class NumberEntity {
        private ReentrantLock lock = new ReentrantLock(true);
        private Condition condition = lock.newCondition();
        private int number;

        public NumberEntity(int number){
            this.number = number;
        }

        public int getNumber(){
            lock.lock();
            try {
                return number;
            }finally {
                lock.unlock();
            }
        }

        /**
         * 递增
         */
        public void increaseNumber(){
            lock.lock();
            try {
                long wait = new Random().nextInt()%100;
                wait = wait<0?-wait:wait;
                System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" increase after sleep: "+ wait);
                TimeUnit.MILLISECONDS.sleep(wait);
                number++;
                condition.signalAll();
                System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" signal all");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }


        /**
         * 获取奇数
         * @return
         */
        public int getOdd(){
            lock.lock();
            try {
                for(;;) {
                    if (number % 2 == 0) {
                        System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" start await, val("+number+")");
                        condition.await();
                        System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" after await, val("+number+")");
                        continue;
                    }
                    return number;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return -1;
        }

        /**
         * 获取偶数
         * @return
         */
        public int getEven(){
            lock.lock();
            try {
                for(;;) {
                    if (number % 2 == 1) {
                        System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" start await, val("+number+")");
                        condition.await();
                        System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" after await, val("+number+")");
                        continue;
                    }
                    return number;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return -1;
        }

    }

    /**
     * 奇数runner
     */
    public static class OddRunner implements Runnable {
        private NumberEntity numberEntity;

        public OddRunner(NumberEntity numberEntity){
            this.numberEntity = numberEntity;
        }

        @Override
        public void run() {
            while (true){
                int number = numberEntity.getOdd();
                System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" get odd  number: "+ number);
                numberEntity.increaseNumber();
                if(number>20){
                    break;
                }
            }
        }
    }

    /**
     * 偶数runner
     */
    public static class EvenRunner implements Runnable {
        private NumberEntity numberEntity;

        public EvenRunner(NumberEntity numberEntity){
            this.numberEntity = numberEntity;
        }

        @Override
        public void run() {
            while (true){
                int number = numberEntity.getEven();
                System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" get even number: "+ number);
                numberEntity.increaseNumber();
                if(number>20){
                    break;
                }
            }
        }
    }


}
