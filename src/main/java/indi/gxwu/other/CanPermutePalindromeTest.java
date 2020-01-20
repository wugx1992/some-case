package indi.gxwu.other;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: gx.wu
 * @Date: 2020/1/6 17:36
 * @Description: 给定一个字符串，判断字符串是否存在一个排列是回文排列。
 */
public class CanPermutePalindromeTest {

    public static void main(String[] args) {
        CanPermutePalindromeTest t = new CanPermutePalindromeTest();
        String s;

        s = "code";
        System.out.println(t.canPermutePalindrome(s));

        s = "aab";
        System.out.println(t.canPermutePalindrome(s));

        s = "carerac";
        System.out.println(t.canPermutePalindrome(s));


        JSONObject object = new JSONObject();
        object.put("number1", "11.1");
        object.put("number2", 12121.221);
        object.put("number3", "abdc@");
        System.out.println(object.optDouble("number1"));
        System.out.println(object.optDouble("number2"));
        System.out.println(object.optDouble("number3"));
    }


    /**
     * @param s: the given string
     * @return: if a permutation of the string could form a palindrome
     */
    public boolean canPermutePalindrome(String s) {
        Map<Character,Integer> map = new HashMap<Character, Integer>();
        for(int i=0;i<s.length();i++) {
            Character c = s.charAt(i);
            if(map.containsKey(c)){
                map.put(c,map.get(c)+1);
            }else{
                map.put(c,1);
            }
        }

        int count = 0;
        for(Map.Entry<Character,Integer> entry : map.entrySet()){
            if(entry.getValue()%2==0){
                continue;
            }else{
                count++;
            }
        }
        if(count>1){
            return false;
        }else{
            return true;
        }
    }


    //    /**
//     * @param s: the given string
//     * @return: if a permutation of the string could form a palindrome
//     */
//    public boolean canPermutePalindrome(String s) {
//        // write your code here
//        for(int i=0;i<s.length();i++){
//            int j=s.length()-1;
//            boolean eq = false;
//            for(;j>i;j--){
//                if(s.charAt(i) == s.charAt(j)){
//                    eq = true;
//                    i++;
//                }else{
//                    eq = false;
//                }
//            }
//            if(eq == true && (i==j || i-1==j)){
//                return true;
//            }
//        }
//        return false;
//    }
}
