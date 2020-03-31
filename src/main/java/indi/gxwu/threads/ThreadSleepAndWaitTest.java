package indi.gxwu.threads;

/**
 * @Author: gx.wu
 * @Date: 2020/3/27 10:33
 * @Description: sleep、wait 对于同步锁的影响
 */
public class ThreadSleepAndWaitTest {


    /** 定义锁 **/
    private static final Object LOCK = new Object();


    public static void startNewThread(Runnable runnable){
        Thread thread = new Thread(runnable);
        thread.start();
        //System.out.println("time: "+System.currentTimeMillis()+", "+Thread.currentThread().getName()+ ", start new thread, current thread localValue: " + ThreadLocalTest.ThreadLocalId.get());
    }


    /**
     * sleep 对于同步锁，会释放cpu，但是不会释放锁
     * @return
     */
    public static Runnable getSynchronizedSleepRunnable(){
        return new Runnable() {
            @Override
            public void run() {
                synchronized (LOCK){
                    try {
                        System.out.println("time: "+System.currentTimeMillis()+", "+Thread.currentThread().getName()+ ", get the lock, current thread localValue: " + ThreadLocalTest.ThreadLocalId.get());
                        Thread.sleep(100);
                        System.out.println("time: "+System.currentTimeMillis()+", "+Thread.currentThread().getName()+ ", release the lock, current thread localValue: " + ThreadLocalTest.ThreadLocalId.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    /**
     * wait 对于同步锁，会释放cpu，且会释放锁
     * @return
     */
    public static Runnable getSynchronizedWaitRunnable(){
        return new Runnable() {
            @Override
            public void run() {
                synchronized (LOCK) {
                    try {
                        System.out.println("time: " + System.currentTimeMillis() + ", " + Thread.currentThread().getName() + ", get the lock, current thread localValue: " + ThreadLocalTest.ThreadLocalId.get());
                        LOCK.wait(100);
                        System.out.println("time: " + System.currentTimeMillis() + ", " + Thread.currentThread().getName() + ", release the lock, current thread localValue: " + ThreadLocalTest.ThreadLocalId.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }




    public static void main(String[] args) throws InterruptedException {
        System.out.println("============= thread sleep test ================");
        System.out.println("sleep 对于同步锁，会释放cpu，但是不会释放锁");
        Thread.sleep(100);

        startNewThread(getSynchronizedSleepRunnable());
        startNewThread(getSynchronizedSleepRunnable());
        startNewThread(getSynchronizedSleepRunnable());
        startNewThread(getSynchronizedSleepRunnable());

        Thread.sleep(3000);
        System.out.println("\n============= thread wait test ================");
        System.out.println("wait 对于同步锁，会释放cpu，且会释放锁");
        Thread.sleep(100);

        startNewThread(getSynchronizedWaitRunnable());
        startNewThread(getSynchronizedWaitRunnable());
        startNewThread(getSynchronizedWaitRunnable());
        startNewThread(getSynchronizedWaitRunnable());

    }
}
