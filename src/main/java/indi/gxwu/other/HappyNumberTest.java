package indi.gxwu.other;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: gx.wu
 * @Date: 2019/12/27 17:54
 * @Description: 快乐数
 */
public class HappyNumberTest {

    public static void main(String[] args) {
        HappyNumberTest t = new HappyNumberTest();
        int n = 19;
        System.out.println(t.isHappy(n));

        n = 5;
        System.out.println(t.isHappy(n));
    }

    public boolean isHappy(int n){
        String numStr = String.valueOf(n);
        Set<String> set = new HashSet<String>();
        set.add(numStr);
        while (true){
            int newValue = 0;
            for(int i=0;i<numStr.length();i++){
                int unit = Integer.valueOf(numStr.substring(i,i+1));
                newValue+= unit*unit;
            }
            String newValueStr = String.valueOf(newValue);
            if(newValue==1){
                return true;
            }else if(set.contains(newValueStr)){
                return false;
            }else{
                set.add(newValueStr);
                numStr = newValueStr;
                continue;
            }
        }
    }
}
