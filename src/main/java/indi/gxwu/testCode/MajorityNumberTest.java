package indi.gxwu.testCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: gx.wu
 * @Date: 2019/12/23 11:07
 * @Description: 主元素
 */
public class MajorityNumberTest {

    public static void main(String[] args) {
        int[] n = new int[]{2,1,1,2,1,2,1,2,1};
        List<Integer> nums = new ArrayList<Integer>();
        for(int i=0;i<n.length;i++){
            nums.add(n[i]);
        }
        MajorityNumberTest t = new MajorityNumberTest();
        int result = t.majorityNumber(nums);
        System.out.println(result);



        int[] n2 = new int[]{2,3,4,2,3,4,1,4,1,1,4};
        List<Integer> nums2 = new ArrayList<Integer>();
        for(int i=0;i<n2.length;i++){
            nums2.add(n2[i]);
        }
        int result2 = t.majorityNumber2(nums2);
        System.out.println(result2);
    }


    /**
     * 主元素(大于数组个数1/2)
     * @param nums
     * @return
     */
    public int majorityNumber(List<Integer> nums){
        int tryMajority = nums.get(0);
        int count = 1;
        for(int i=1;i<nums.size();i++){
            int currentNum = nums.get(i);
            if(tryMajority != currentNum){
                count--;
                if(count==-1){
                    tryMajority = currentNum;
                    count = 1;
                }
            }else{
                count++;
            }
        }
        if(count>0){
            return tryMajority;
        }else{
            return 0;
        }
    }

    /**
     * 主元素(大于数组个数1/3)
     * @param nums
     * @return
     */
    public int majorityNumber2(List<Integer> nums){
        int tryMajority1 = 0, tryMajority2 = 0;
        int count1=0, count2 = 0;
        for(int i=0;i<nums.size();i++){
            if(tryMajority1 == nums.get(i)){
                count1++;
            }else if(tryMajority2 == nums.get(i)){
                count2++;
            }else if(count1 == 0){
                tryMajority1 = nums.get(i);
                count1 = 1;
            }else if(count2 == 0){
                tryMajority2 = nums.get(i);
                count2 = 1;
            }else{
                count1--;
                count2--;
            }
        }
        count1 = count2 = 0;
        for(int i=0;i<nums.size();i++){
            if(tryMajority1 == nums.get(i)){
                count1++;
            }else if(tryMajority2 == nums.get(i)){
                count2++;
            }else {
                continue;
            }
        }

        return count1>count2?tryMajority1:tryMajority2;
    }
}
