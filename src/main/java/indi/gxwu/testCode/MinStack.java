package indi.gxwu.testCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: gx.wu
 * @Date: 2019/12/18 17:40
 * @Description: code something to describe this module what it is
 */
public class MinStack {

    private List<Integer> stackValue;
    private List<Integer> minValue;

    public MinStack() {
        // do intialization if necessary
        stackValue = new ArrayList<Integer>();
        minValue = new ArrayList<Integer>();
    }

    /*
     * @param number: An integer
     * @return: nothing
     */
    public void push(int number) {
        // write your code here
        stackValue.add(number);
        if(minValue.size()==0){
            minValue.add(number);
        }else{
            int val = minValue.get(minValue.size()-1);
            minValue.add(val<number?val:number);
        }
    }

    /*
     * @return: An integer
     */
    public int pop() {
        // write your code here
        if(stackValue.size()>0){
            minValue.remove(minValue.size()-1);
            return stackValue.remove(stackValue.size()-1);
        }else {
            return 0;
        }
    }

    /*
     * @return: An integer
     */
    public int min() {
        // write your code here
        return minValue.get(minValue.size()-1);
    }
}
