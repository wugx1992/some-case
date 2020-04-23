package indi.gxwu.testCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: gx.wu
 * @Date: 2020/1/2 10:59
 * @Description: 两数之和：给一个整数数组，找到两个数使得他们的和等于一个给定的数 target
 */
public class TwoSumTest {

    public static void main(String[] args) {
        TwoSumTest t = new TwoSumTest();
        int numbers1[] = new int[]{2, 7, 11, 15};
        int[] r1 = t.twoSum(numbers1, 9);
        System.out.println(r1[0]+" "+r1[1]);

        int numbers2[] = new int[]{15, 2, 7, 11};
        int[]r2 = t.twoSum(numbers2, 9);
        System.out.println(r2[0]+" "+r2[1]);


        int numbers3[] = new int[]{15, 5, 7, 5};
        int[]r3 = t.twoSum(numbers3, 10);
        System.out.println(r3[0]+" "+r3[1]);

    }

    /**
     * @param numbers: An array of Integer
     * @param target: target = numbers[index1] + numbers[index2]
     * @return: [index1, index2] (index1 < index2)
     */
    public int[] twoSum(int[] numbers, int target) {
        // write your code here
        Map<Integer,Integer> indexMap = new HashMap<Integer, Integer>();
        for(int i=0;i<numbers.length;i++){
            indexMap.put(numbers[i],i);
        }
        int[] result = new int[2];
        for(int i=0;i<numbers.length;i++){
            int remain = target - numbers[i];
            if(indexMap.containsKey(remain)){
                int index = indexMap.get(remain);
                if(index==i){
                    continue;
                }
                result[0] = index<i?index:i;
                result[1] = i>index?i:index;
            }
        }
        return result;
    }

}
