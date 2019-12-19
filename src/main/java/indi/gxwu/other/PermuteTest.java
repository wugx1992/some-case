package indi.gxwu.other;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: gx.wu
 * @Date: 2019/12/19 11:37
 * @Description: 全排序
 */
public class PermuteTest {

    public static void main(String[] args) {
        PermuteTest t = new PermuteTest();
        int[] nums = {1,2,3,4};
        List<List<Integer>> result = t.permute(nums);
        System.out.println(result.size());
        for(List<Integer> v : result){
            System.out.println(v);
        }
    }


    /**
     * @param nums: A list of integers.
     * @return: A list of permutations.
     */
    public List<List<Integer>> permute(final int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(nums.length==0){
            result.add(new ArrayList<Integer>());
            return result;
        }
        //初始化第一个组合
        List<Integer> initVal = new ArrayList<Integer>(){{
            add(nums[0]);
        }};
        result.add(initVal);

        for(int i=1;i<nums.length;i++){
            //待组合因子
            int collVal = nums[i];
            List<List<Integer>> newRs = new ArrayList<List<Integer>>();
            //用组合因子，遍历现有的组合，形成新的组合
            for(int j=0;j<result.size();j++){
                List<Integer> collocation = result.get(j);
                //index=1开始插入，形成新组合
                for(int k=1;k<=collocation.size();k++){
                    //新组合
                    List<Integer> temp = new ArrayList<Integer>(collocation);
                    temp.add(k, collVal);
                    newRs.add(temp);
                }
                //index=0位置插入，形成新组合
                collocation.add(0,collVal);
            }
            if(newRs.size()!=0){
                result.addAll(newRs);
            }
//            for(List<Integer> v : result){
//                System.out.println(v);
//            }
//            System.out.println("------------------------------");
        }

        return result;
    }









}
