package indi.gxwu.designModel;

/**
 * @Author: gx.wu
 * @Date: 2019/3/21 16:35
 */
public class SingletonTest {

    public static void main(String[] args) {

        MySingleton mySingleton = MySingleton.getInstance();
        mySingleton.sayHello();

        MySingleton mySingleton2 = MySingleton.getInstance();
        mySingleton2.sayHello();
    }
}
