package indi.gxwu.other;

import lombok.Data;

import java.util.function.Consumer;

/**
 * @Author: gx.wu
 * @Date: 2020/5/6 11:26
 * @Description: code something to describe this module what it is
 */
public class OutClassTest {

    private int value;
    private static int sv;

    public OutClassTest(int value) {
        this.value = value;
    }

    private class InnerClassA{
        private int v;
        public int getOutSideValue(){
            return value;
        }
        public int getOutSideSV(){
            return sv;
        }
    }

    @Data
    class InnerClassB{
        private int v;
        public int getOutSideValue(){
            return value;
        }
        public int getOutSideSV(){
            return sv;
        }
    }

    @Data
    protected class InnerClassC{
        private int v;
        public int getOutSideValue(){
            return value;
        }
        public int getOutSideSV(){
            return sv;
        }
    }

    @Data
    public class InnerClassD{
        private int v;
        public int getOutSideValue(){
            return value;
        }
        public int getOutSideSV(){
            return sv;
        }
    }

    @Data
    public static class InnerClassE{
        private int v;

        /**
         * 编译错误
         */
//        public int getOutSideValue(){
//            return value;
//        }
        public int getOutSideSV(){
            return sv;
        }
    }

    public static void print(Class c, int v, Consumer<Object> consumer){
        consumer.accept(c.getName()+" value: "+v);
    }

    public static void main(String[] args) {
        /**
         * 编译错误
         */
//        InnerClassD d = OutClassTest.this.new InnerClassD();
        InnerClassE e = new OutClassTest.InnerClassE();
        e.setV(2343);
        print(e.getClass(), e.getV(), System.out::println);

        OutClassTest outClass = new OutClassTest(12);
        print(outClass.getClass(), outClass.value, System.out::println);

        InnerClassA a = outClass.new InnerClassA();
        a.v = 12;
        System.out.println(a.v);

        InnerClassB b = outClass.new InnerClassB();
        InnerClassC c = outClass.new InnerClassC();
        InnerClassD d = outClass.new InnerClassD();


    }

}
