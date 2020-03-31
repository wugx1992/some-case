package indi.gxwu.threads;

/**
 * @Author: gx.wu
 * @Date: 2020/3/19 16:11
 * @Description: code something to describe this module what it is
 */
public class ThreadA extends Thread {

    @Override
    public void run(){
        try {
            ConditionTest.lock.lock();
            System.out.println("进入线程A " + Thread.currentThread().getName()+" QueueLength："+ConditionTest.lock.getQueueLength());
            ConditionTest.conditionB.signal();
            ConditionTest.conditionA.await();
            System.out.println("进入线程A " + Thread.currentThread().getName()+" await 之后");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ConditionTest.lock.unlock();
        }
    }
}
