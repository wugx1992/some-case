package indi.gxwu.designModel;

/**
 * @Author: gx.wu
 * @Date: 2019/3/21 16:21
 */
public class MySingleton {

    private static class MySingletonHolder{
        private static MySingleton instance = new MySingleton();
    }

    private MySingleton(){
        System.out.println("create singleton object");
    }

    public static MySingleton getInstance(){
        return MySingletonHolder.instance;
    }

    public void sayHello(){
        System.out.println(this+"  hello world!");
    }

}
