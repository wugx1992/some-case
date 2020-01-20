package indi.gxwu.other;

/**
 * @Author: gx.wu
 * @Date: 2020/1/17 16:52
 * @Description:
 * 数组除了自身的乘积
 *
 * 给定n个整数的数组nums，其中n> 1，返回一个数组输出，使得output [i]等于nums的所有除了nums [i]的元素的乘积。
 */
public class ProductExceptSelfTest {

    public static void main(String[] args) {
        ProductExceptSelfTest t = new ProductExceptSelfTest();
        int[] t1 = new int[]{1,2,3,4};
        int[] result1 = t.productExceptSelf(t1);
        t.print(result1);

        int[] t2 = new int[]{2,3,8};
        int[] result2 = t.productExceptSelf(t2);
        t.print(result2);

        int[] t3 = new int[]{0,0,0};
        int[] result3 = t.productExceptSelf(t3);
        t.print(result3);

        int[] t4 = new int[]{3,0,2};
        int[] result4 = t.productExceptSelf(t4);
        t.print(result4);


    }
    public void print(int[] data){
        System.out.printf("[");
        for(int i=0;i<data.length;i++){
            System.out.printf(data[i]+",");
        }
        System.out.printf("]\n");
    }

    /**
     * @param nums: an array of integers
     * @return: the product of all the elements of nums except nums[i].
     */
    public int[] productExceptSelf(int[] nums) {
        // write your code here
        int total = 1;
        int[] result = new int[nums.length];
        int zeroAppearCount = 0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]!=0){
                total *= nums[i];
            }else{
                zeroAppearCount++;
            }
        }

        for(int i=0;i<nums.length;i++){
            if(zeroAppearCount>1){
                result[i] = 0;
            }else if(zeroAppearCount == 1){
                if(nums[i]==0){
                    result[i] = total;
                }else{
                    result[i] = 0;
                }
            }else{
                result[i] = total/nums[i];
            }
        }
        return result;
    }
}
