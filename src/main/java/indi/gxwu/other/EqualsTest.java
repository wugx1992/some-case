package indi.gxwu.other;

import java.lang.reflect.Field;

/**
 * @Author: gx.wu
 * @Date: 2020/1/14 14:08
 * @Description: code something to describe this module what it is
 */
public class EqualsTest {


    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        EqualsTest t = new EqualsTest();
        t.test1();
        t.test2_1();
        t.test2_2();
        t.test3();
        t.test4();
    }

    /**
     * String 中的 equals 方法是被重写过的，因为 object 的 equals 方法是比较的对象的内存地址，而 String 的 equals 方法比较的是对象的值。
     * 当创建 String 类型的对象时，虚拟机会在常量池中查找有没有已经存在的值和要创建的值相同的对象，如果有就把它赋给当前引用。如果没有就在常量池中重新创建一个 String 对象。
     */
    public void test1(){
        System.out.println("-----------------------");
        System.out.println("equals 与 ==");
        // a 为一个引用
        String a = new String("ab");
        // b为另一个引用,对象的内容一样
        String b = new String("ab");
        // 放在常量池中
        String aa = "ab";
        // 从常量池中查找
        String bb = "ab";
        // true
        System.out.println("aa==bb：" + (aa == bb));
        // false，非同一对象
        System.out.println("a==b：" + (a == b));
        // true
        System.out.println("aEQb：" + a.equals(b));
        // true
        System.out.println("42 == 42.0：" + (42 == 42.0));
        System.out.println("-----------------------");
    }

    /**
     * String 的不可变性，内部final修饰
     */
    public void test2_1(){
        System.out.println("-----------------------");
        System.out.println("String的“不可变”");
        String t1 = "hello ";
        String t2 = t1;
        System.out.println("t1 hasCode: "+t1.hashCode());
        System.out.println("t2 hasCode: "+t2.hashCode());
        t1 = t1+"world";
        System.out.println("t1 hasCode: "+t1.hashCode());
        System.out.println("t2 hasCode: "+t2.hashCode());
        System.out.println("t1: "+t1);
        System.out.println("t2: "+t2);
        System.out.println("-----------------------");
    }

    /**
     * 反射方式，修改String内部char变量，“不可变”->"可变"
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void test2_2() throws NoSuchFieldException, IllegalAccessException {
        System.out.println("-----------------------");
        System.out.println("反射方式修改String的“不可变”");
        // 创建字符串"Hello World"， 并赋给引用s
        String s = "Hello World";
        System.out.println("s = " + s);
        // 获取String类中的value字段
        Field valueFieldOfString = String.class.getDeclaredField("value");
        // 改变value属性的访问权限
        valueFieldOfString.setAccessible(true);
        // 获取s对象上的value属性的值
        char[] value = (char[]) valueFieldOfString.get(s);
        // 改变value所引用的数组中的第5个字符
        value[5] = '_';
        System.out.println("s = " + s);
        System.out.println("-----------------------");
    }

    /**
     * 类的hashCode、equals 关系
     */
    public void test3(){
        System.out.println("-----------------------");
        System.out.println("hashCode 与 equals");
        Person p1 = new Person(11, "jack", "somewhere");
        Person p2 = new Person(11, "jack", "somewhere");
        Person p3 = p1;
        System.out.println(p1.hashCode());
        System.out.println(p2.hashCode());
        System.out.println(p3.hashCode());

        System.out.println(p1.equals(p2));
        System.out.println(p1.equals(p3));
        System.out.println(p2.equals(p3));
        System.out.println("-----------------------");

    }


    /**
     * java 函数都是按值传递调用，对象引用实际也是引用地址的按值传递
     */
    public void test4(){
        System.out.println("-----------------------");
        Person p1 = new Person(12, "LiMing", "P1福田");
        Person p2 = new Person(23, "DongMei", "P2南山");
        System.out.println("交换前：");
        System.out.println("p1: "+p1.toString());
        System.out.println("p2: "+p2.toString());
        swap(p1,p2);
        System.out.println("交换后（实际并不能进行交互）：");
        System.out.println("p1: "+p1.toString());
        System.out.println("p2: "+p2.toString());
        System.out.println("-----------------------");
    }

    private void swap(Person p1, Person p2){
        Person temp = p1;
        p1 = p2;
        p2 = temp;
        System.out.println("尝试交换：");
        System.out.println("p1: "+p1.toString());
        System.out.println("p2: "+p2.toString());
    }

    class Person{
        int age;
        String name;
        String address;

        public Person(int age, String name, String address){
            this.age = age;
            this.name = name;
            this.address = address;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }
    }
}
