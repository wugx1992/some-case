package indi.gxwu.other;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: gx.wu
 * @Date: 2020/4/27 11:32
 * @Description: <? extends T>:上界通配符、<? super T>:下界通配符 测试
 */
public class SuperAndExtendTest {

    @Data
    public static class A{
        private String a = "a";
    }

    @Data
    public static class B extends A{
        private String b = "b";
    }

    @Data
    public static class C extends B{
        private String c = "c";
    }

    @Data
    public static class D extends C{
        private String d = "d";
    }

    @Data
    public static class E extends C{
        private String e = "e";
    }

    @Data
    public static class F extends E{
        private String f = "f";
    }

    public static void main(String[] args) {
        A obj1 = new B();
        System.out.println(obj1.getA());
        System.out.println(((B)obj1).getB());
        try {
            System.out.println(((C) obj1).getC());
        }catch (Exception e){
            e.printStackTrace();
        }

//        ArrayList<A> list1 = new ArrayList<B>();      //编译不通过
        List<? extends C> list2 = new ArrayList<D>();
//        List<? extends C> list3 = new ArrayList<B>();   //编译不通过
        List<? extends C> list4 = new ArrayList<E>();
        List<? extends C> list5 = new ArrayList<F>();

        List<? super C> list6 = new ArrayList<B>();
//        List<? super C> list7 = new ArrayList<D>();     //编译不通过
        List<? super C> list8 = new ArrayList<A>();



    }




}
