package indi.gxwu.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: gx.wu
 * @Date: 2019/12/10 15:53
 * @Description: 多线程处理10000000累加
 */
public class ArraySumExample {

    public static int maxNum = 100000000;

    public static void main(String[] args) {
        long value1 = test01(maxNum);

//        long value2 = test02(maxNum);
//        System.out.println(value2);

        long value3 = test03(maxNum);



    }

    /**
     * 单线程处理
     * @param maxNum
     * @return
     */
    public static long test01(int maxNum){
        long startTimeMillis = System.currentTimeMillis();
        long result = 0;
        for(int i=1;i<=maxNum;i++){
            result+= i;
        }
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("单线程结束任务，耗时："+(endTimeMillis-startTimeMillis)+"，结果："+result);
        return result;
    }


    /**
     * 递归出现堆栈溢出
     * @param num
     * @return
     */
    public static long test02(int num){
        if(num==1){
            return 1;
        }else{
            return num+test02(num-1);
        }
    }


    /**
     * 多线程处理，每个线程处理5000000
     * @param maxNum
     * @return
     */
    public static long test03(int maxNum){
        long startTimeMillis = System.currentTimeMillis();
        long result = 0;
        ExecutorService executorService = new ThreadPoolExecutor(100, 100, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        int threadTaskLength = 5000000;
        int threadSize = maxNum/threadTaskLength+1;
        boolean special = maxNum%threadTaskLength == 0;
        List<Callable<Long>> callableList = new ArrayList<Callable<Long>>();

        for(int i=0;i<threadSize; i++){
            final int startNum;
            final int taskLength;
            if(i==threadSize-1){
                if(special){
                   break;
                }
                startNum = i*threadTaskLength+1;
                taskLength = maxNum%threadTaskLength;
            }else{
                startNum = i*threadTaskLength+1;
                taskLength = threadTaskLength;
            }
//            System.out.println("组："+i+" startNum："+startNum+" taskLength："+taskLength);


            Callable<Long> callable = new Callable<Long>() {
                public Long call() throws Exception {
                    long callResult = 0;
                    for(int offset=0;offset<taskLength;offset++){
                        callResult += startNum + offset;
                    }
                    return callResult;
                }
            };
            callableList.add(callable);
        }

        try {
            List<Future<Long>> futureList = executorService.invokeAll(callableList);
            for(Future<Long> future : futureList){
                result += future.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("多线程结束任务，耗时："+(endTimeMillis-startTimeMillis)+"，结果："+result);
        return result;
    }

}


