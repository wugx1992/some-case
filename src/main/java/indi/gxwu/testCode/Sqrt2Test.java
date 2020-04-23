package indi.gxwu.testCode;

import java.math.BigDecimal;

/**
 * @Author: gx.wu
 * @Date: 2020/1/3 11:58
 * @Description: 已知 sqrt (2)约等于 1.414，要求不用数学库，求 sqrt (2)精确到小数点后 10 位。
 */
public class Sqrt2Test {

    public static void main(String[] args) {
        Sqrt2Test t = new Sqrt2Test();
        System.out.println(t.sqrt2());
        System.out.println(Math.sqrt(2));
    }

    public double sqrt2(){
        BigDecimal precision = new BigDecimal(0.0000000001);
        BigDecimal low = new BigDecimal(1.4), high = new BigDecimal(1.5);
        BigDecimal mid;
        while (true){
            mid = low.add(high).divide(BigDecimal.valueOf(2));
            if(high.subtract(low).subtract(precision).doubleValue()<=0){
                return mid.doubleValue();
            }
            if(mid.multiply(mid).doubleValue()>2){
                high = mid;
            }else{
                low = mid;
            }
        }
    }
}
