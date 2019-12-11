package indi.gxwu.other;

import java.util.concurrent.Callable;

/**
 * @Author: gx.wu
 * @Date: 2019/6/6 17:56
 */
public class ThreadCase {

}

class Thread1 implements Runnable{

    public void run() {

    }
}


class Thread2 extends Thread{

}



class Thread3 implements Callable<Integer>{

    public Integer call() throws Exception {
        return null;
    }
}