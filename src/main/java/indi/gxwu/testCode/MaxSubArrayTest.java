//package indi.gxwu.testCode;
//
///**
// * @Author: gx.wu
// * @Date: 2019/12/24 11:46
// * @Description: 最大子数组
// */
//public class MaxSubArrayTest {
//
//    public static void main(String[] args) {
//        MaxSubArrayTest t = new MaxSubArrayTest();
//        int[] nums = new int[]{-2,2,-3,4,-1,2,1,-5,3};
//        System.out.println(t.maxSubArray(nums));
//
//        int[] nums2 = new int[]{1,2,3,4};
//        System.out.println(t.maxSubArray(nums2));
//
//
//    }
//
//    public int maxSubArray(int[] nums){
//        if(nums.length==1){
//            return nums[0];
//        }
//        int startIndex=0, endIndex=1;
//        int sum = nums[0];
//        int trySum = nums[0];
//        int i =1;
//
//        for(;i<nums.length;i++){
//            int tempTry = trySum+nums[i];
//            if(tempTry>=trySum){
//                trySum += nums[i];
//                sum = trySum;
//                endIndex = i;
//            }else{
//                trySum += nums[i];
//            }
//
//            if(trySum<(trySum-nums[startIndex])){
//                trySum = trySum-nums[startIndex];
//                startIndex++;
//            }
//        }
//
//        endIndex = endIndex==0?1:endIndex;
//        for(int j=startIndex;j<=endIndex;j++){
//            System.out.printf(nums[j]+" ");
//        }
//        System.out.println();
//        return sum;
//    }
//}
