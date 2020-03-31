package indi.gxwu.threads;

/**
 * @Author: gx.wu
 * @Date: 2020/3/19 16:11
 * @Description: code something to describe this module what it is
 */
public class ThreadC extends Thread {

    @Override
    public void run(){
        try {
            ConditionTest.lock.lock();
            System.out.println("进入线程C " + Thread.currentThread().getName()+" QueueLength："+ConditionTest.lock.getQueueLength());
            ConditionTest.conditionA.signal();
            ConditionTest.conditionC.await();
            System.out.println("进入线程C " + Thread.currentThread().getName()+" await 之后");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ConditionTest.lock.unlock();
        }
    }
}
