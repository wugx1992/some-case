package indi.gxwu.other;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: gx.wu
 * @Date: 2020/1/17 17:09
 * @Description: code something to describe this module what it is
 */
public class MaxStack {

    public static void main(String[] args) {
        MaxStack t = new MaxStack();
        t.push(5);
        t.push(1);
        t.push(5);
        System.out.println(t.top());
        System.out.println(t.popMax());
        System.out.println(t.top());
        System.out.println(t.peekMax());
        System.out.println(t.pop());
        System.out.println(t.top());
    }

    private List<Integer> list;
    public MaxStack() {
        // do intialization if necessary
        list = new LinkedList<Integer>();
    }

    /**
     * @param x: An integer
     * @return: nothing
     */
    public void push(int x) {
        // write your code here
        list.add(x);
    }

    public int pop() {
        // write your code here
        if(list.size()>0) {
            return list.remove(list.size() - 1);
        }else {
            return 0;
        }
    }

    /**
     * @return: An integer
     */
    public int top() {
        // write your code here
        if(list.size()>0) {
            return list.get(list.size() - 1);
        }else {
            return 0;
        }
    }

    /**
     * @return: An integer
     */
    public int peekMax() {
        // write your code here
        if(list.size()==0){
            return 0;
        }
        int max = list.get(list.size()-1);
        int index = list.size()-1;
        for(int i=list.size()-1;i>=0;i--){
            if(list.get(i)>max){
                max = list.get(i);
                index = i;
            }
        }
        return max;
    }

    /**
     * @return: An integer
     */
    public int popMax() {
        // write your code here
        int max = list.get(list.size()-1);
        int index = list.size()-1;
        for(int i=list.size()-1;i>=0;i--){
            if(list.get(i)>max){
                max = list.get(i);
                index = i;
            }
        }
        list.remove(index);
        return max;
    }



}
