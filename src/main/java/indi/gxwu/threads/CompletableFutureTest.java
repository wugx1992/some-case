package indi.gxwu.threads;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * @Author: gx.wu
 * @Date: 2020/4/24 16:57
 * @Description: code something to describe this module what it is
 */
public class CompletableFutureTest {


    /**
     * CompletableFuture supplyAsync 用法
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void test1() throws ExecutionException, InterruptedException {
        ExecutorService es = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

        Future<String> future = es.submit(()->{
            System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" isDaemon: "+Thread.currentThread().isDaemon());
            return "first task result";
        });

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->{
            System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" isDaemon: "+Thread.currentThread().isDaemon());
            System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" executed by forkJoinPool");
            return "second task result";
        });

        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(()->{
            System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" isDaemon: "+Thread.currentThread().isDaemon());
            System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" executed by ThreadPoolExecutor");
            return "third task result";
        }, es);

        System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" "+future.get());
        System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" "+completableFuture.get());
        System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" "+completableFuture2.get());
        es.shutdown();
    }


    /**
     * CompletableFuture allOf
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void test2() throws ExecutionException, InterruptedException {
        ExecutorService es = new ThreadPoolExecutor(2, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(new MySupplier(), es);
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(()->{
            System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" isDaemon: "+Thread.currentThread().isDaemon());
            try {
                TimeUnit.MILLISECONDS.sleep(1*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "result of lambda supplier";
        }, es);

        CompletableFuture future = CompletableFuture.allOf(completableFuture, completableFuture2);
        System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" 2 of future all complete "+future.get());
        System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" "+completableFuture.get());
        System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" "+completableFuture2.get());
        es.shutdown();
    }

    public static void test3(){
        ExecutorService es = new ThreadPoolExecutor(2, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        CompletableFuture.supplyAsync(()->{
            System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" isDaemon: "+Thread.currentThread().isDaemon());
            return "first lambda result";
        }, es).thenAccept(p->{
            System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" second step");
            System.out.println("second lambda result, " + p+" "+p.getClass());
        });

        es.shutdown();
    }

    public static class MySupplier implements Supplier {
        @Override
        public Object get() {
            System.out.println(System.currentTimeMillis()+" "+Thread.currentThread().getName()+" isDaemon: "+Thread.currentThread().isDaemon());
            try {
                TimeUnit.MILLISECONDS.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "result of MySupplier";
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
        TimeUnit.MILLISECONDS.sleep(2*1000);
        System.out.println("--------------------------------------------");
        test2();
        TimeUnit.MILLISECONDS.sleep(2*1000);
        System.out.println("--------------------------------------------");
        test3();
    }


}
