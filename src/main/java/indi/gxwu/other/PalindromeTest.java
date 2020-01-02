package indi.gxwu.other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: gx.wu
 * @Date: 2019/12/27 10:07
 * @Description: 有效回文串
 */
public class PalindromeTest {

    public static void main(String[] args) {
        PalindromeTest t = new PalindromeTest();
        String str = "A man, a plan, a canal: Panama";
        System.out.println(t.isPalindrome(str));

        str = "12Dege12DD";
        System.out.println(t.isPalindrome(str));

        str = "";
        System.out.println(t.isPalindrome(str));

        str = "ab2a";
        System.out.println(t.isPalindrome(str));
    }

    public boolean isPalindrome(String s){
        if(s==null){
            return false;
        }

        String reg = "[a-zA-Z0-9]";
        Pattern p = Pattern.compile(reg);
        String left = null;
        String right = null;
        for(int i=0,j=s.length()-1;i<=j;){
            if(left==null) {
                left = ((Character) s.charAt(i)).toString();
                Matcher m = p.matcher(left);
                if (!m.find()) {
                    i++;
                    left = null;
                    continue;
                }
            }
            if(right==null){
                right = ((Character)s.charAt(j)).toString();
                Matcher m = p.matcher(right);
                if(!m.find()){
                    j--;
                    right = null;
                    continue;
                }
            }
            if(!left.equalsIgnoreCase(right)){
                return false;
            }else{
                left = null;
                right = null;
                i++;
                j--;
            }
        }
        return true;
    }
}
