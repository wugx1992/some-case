package indi.gxwu.testCode;

import java.util.Set;
import java.util.TreeSet;

/**
 * @Author: gx.wu
 * @Date: 2019/6/5 15:30
 */
public class Case02 implements Comparable<Case02>{
    private String name;
    private int age;

    public Case02(){}

    public Case02(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Case02{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public int compareTo(Case02 o) {
        return this.age - o.age;
    }



    public static void main(String[] args) {
        Set<Case02> set = new TreeSet<Case02>();
        set.add(new Case02("小明", 12));
        set.add(new Case02("丽丽", 10));
        set.add(new Case02("小黄", 23));
        set.add(new Case02("小白", 20));

        System.out.println(set);

    }
}
