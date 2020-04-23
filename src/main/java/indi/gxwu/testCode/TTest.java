package indi.gxwu.testCode;



/**
 * @Author: gx.wu
 * @Date: 2019/12/18 11:35
 * @Description: code something to describe this module what it is
 */
public class TTest {

    public static void main(String[] args) {

        System.out.println(digitCounts(0,99));


    }

    public static int digitCounts(int k, int n) {
        // write your code here
        int count = 0;
        for(int i=0;i<=n;i++){
            if(i==k){
                count++;
                continue;
            }

            int sum =0;
            int t = 1;
            while(true){
                int v1 = i%(tenPow(t));
                int v2 = i/(tenPow(t));
                int v3 = t==1?(v1-sum):((v1-sum)/(tenPow(t-1)));
                sum = v1;
                if(v3==k){
                    count++;
                }
                t++;
                if(v2==0){
                    break;
                }
            }
        }
        return count;
    }

    public static int tenPow(int m){
        int result = 1;
        for(int i=1;i<=m;i++){
            result *= 10;
        }
        return result;
    }

}
