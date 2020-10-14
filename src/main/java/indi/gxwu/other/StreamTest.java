package indi.gxwu.other;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: gx.wu
 * @Date: 2020/4/23 15:12
 * @Description: code something to describe this module what it is
 */
public class StreamTest {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }


    @Data
    @AllArgsConstructor
    public static class Employee{
        private String id;
        private String name;
        private double salary;
        private String gender;
    }


    public static List<Employee> getEmployeeList(){
        List<Employee> result = new ArrayList<Employee>(){{
            add(new Employee("ID001", "小明", 10000, "male"));
            add(new Employee("ID002", "小强", 8000, "male"));
            add(new Employee("ID003", "李雷", 12000, "male"));
            add(new Employee("ID004", "小红", 5000, "female"));
            add(new Employee("ID005", "小霞", 11000, "female"));
            add(new Employee("ID005", "小霞", 11000, "female"));
            add(new Employee("ID006", "小妮", 8000, "female"));
        }};
        return result;
    }

    /**
     * 筛选和分片
     */
    public static void test1(){
        List<Employee> list = getEmployeeList();
        System.out.println("------------ 【stream 筛选和分片】 -------------");

        System.out.println("--- 【forEach，查询所有数据】");
        list.stream().forEach(e->{
            System.out.println(e);
        });

        System.out.println("--- 【filter，过滤出所有female】");
        list.stream().filter(e->"female".equals(e.getGender())).forEach(System.out::println);

        System.out.println("--- 【distinct，通过对象的hashCode()、equals()去重】");
        list.stream().distinct().forEach(System.out::println);

        System.out.println("--- 【limit，筛选出指定的条数】");
        list.stream().limit(2).forEach(System.out::println);

        System.out.println("--- 【skip，跳过n条记录】");
        list.stream().skip(2).forEach(System.out::println);

        System.out.println("--- 【测试转换出来的list是否真的拷贝】");
        List<Employee> filterResult = list.stream().filter(e->"female".equals(e.getGender())).collect(Collectors.toList());
        System.out.println("两个list hashCode: "+list.hashCode()+" "+filterResult.hashCode());
        System.out.println("两个对象 hashCode: "+list.get(list.size()-1).hashCode()+" "+filterResult.get(filterResult.size()-1).hashCode());
        list.get(list.size()-1).setId("ID100");
        System.out.println("两个对象(并非拷贝操作): "+list.get(list.size()-1)+" "+filterResult.get(filterResult.size()-1));

        System.out.println("------------ [stream 筛选和分片] -------------");
    }

    /**
     * 映射
     */
    public static void test2(){
        List<Employee> list = getEmployeeList();
        System.out.println("------------ 【stream 映射】 -------------");
        System.out.println("--- 【map, 接受一个函数作为参数，并将函数应用到每一个元素上，返回新的元素】");
        list.stream().map(e->e.getSalary()).forEach(System.out::println);

        System.out.println("--- 【sorted, 排序返回新的流】");
        list.stream().map(e->e.getSalary()).sorted((a,b)-> a>b?-1:1).forEach(System.out::println);
        System.out.println("------------ [stream 映射] -------------");
    }

    /**
     * 查找与匹配
     */
    public static void test3(){
        List<Employee> list = getEmployeeList();
        System.out.println("------------ 【stream 查找与匹配】 -------------");
        boolean match1 = list.stream().allMatch(e->e.getSalary()==10000);
        System.out.println("--- allMatch: "+ match1);

        boolean match2 = list.stream().anyMatch(e->e.getSalary()==10000);
        System.out.println("--- anyMatch: "+ match2);

        boolean match3 = list.stream().noneMatch(e->e.getSalary()==10000);
        System.out.println("--- noneMatch: "+ match3);

        Employee employee = list.stream().findFirst().get();
        System.out.println("--- findFirst: "+employee);
        System.out.println("--- findAny: "+list.stream().findAny().toString());
        System.out.println("--- count: "+list.stream().count());
        System.out.println("--- collect list: "+list.stream().filter(e->e.getSalary()==8000).collect(Collectors.toList()));
        System.out.println("--- collect set: "+list.stream().filter(e->e.getSalary()==8000).collect(Collectors.toSet()));
        System.out.println("--- collect groupBy: "+list.stream().filter(e->e.getSalary()==8000).collect(Collectors.groupingBy(e->e.getName())));



        System.out.println("------------ [stream 查找与匹配] -------------");

    }

}
