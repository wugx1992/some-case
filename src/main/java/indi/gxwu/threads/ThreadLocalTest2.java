package indi.gxwu.threads;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: gx.wu
 * @Date: 2020/3/27 10:33
 * @Description:  threadLocal 与 局部变量
 *
 * ThreadLocal 不是为了满足多线程安全而开发出来的，因为局部变量已经足够安全。ThreadLocal 是为了方便线程处理自己的某种状态。
 * 可以看到ThreadLocal 实例化所处的位置，是一个线程共有区域。好比一个银行和个人，我们可以把钱存在银行，也可以把钱存在家。
 * 存在家里的钱是局部变量，仅供个人使用；存在银行里的钱也不是说可以让别人随便使用，只有我们以个人身份去获取才能得到。
 * 所以说ThreadLocal 封装的变量我们是在外面某个区域保存了处于我们个人的一个状态，只允许我们自己去访问和修改的状态。
 * ThreadLocal 同时提供了初始化的机制，在实例化时重写 initialValue() 方法，便可实现变量的初始化工作。
 * 其实可以完全使用参数传递内部参数，就像我在自己随便放钱一样！这里要注意的是，我们定义一个方法的时候，并不是参数越多越好，
 * 有些共有参数，我们应该尽量设为全局，便于系统的可维护性与可扩展性。另一个角度考虑，银行也有其存在的价值，
 * ThreadLocal 会简化我们的编程，毕竟它是安全的。

 */
public class ThreadLocalTest2 {

    private static Object LOCK = new Object();

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



    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 3, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        ThreadLocalTest2 obj = new ThreadLocalTest2();
        threadPool.execute(obj.new SelfRunnable(1));
        threadPool.execute(obj.new SelfRunnable(2));
        threadPool.execute(obj.new SelfRunnable(3));
        threadPool.execute(obj.new SelfRunnable(4));
        threadPool.execute(obj.new SelfRunnable(5));

        threadPool.shutdown();
    }

    class SelfRunnable implements Runnable{
        private int innerValue;
        public SelfRunnable (int value){
            innerValue = value;
        }

        @Override
        public void run() {
            try {
                synchronized(LOCK) {
                    for (int i = 0; i < 3; i++) {
                        System.out.println("time: " + System.currentTimeMillis() + ", " + Thread.currentThread().getName() + ", i: " + i + ", innerValue: "+innerValue+", current thread localValue: " + ThreadLocalId.get());
                        LOCK.wait(100);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ThreadLocalId.remove();
            }
        }
    }
}
