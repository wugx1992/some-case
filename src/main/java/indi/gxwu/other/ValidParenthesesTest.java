package indi.gxwu.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: gx.wu
 * @Date: 2019/12/26 18:03
 * @Description: code something to describe this module what it is
 */
public class ValidParenthesesTest {

    public static void main(String[] args) {
        ValidParenthesesTest t = new ValidParenthesesTest();
        String str = "([)]";
        System.out.println(t.isValidParentheses(str));

        str = "()[]{}";
        System.out.println(t.isValidParentheses(str));

        str = "({[]})";
        System.out.println(t.isValidParentheses(str));
    }

    public boolean isValidParentheses(String s){
        List<Character> ps = new ArrayList<Character>();
        Map<Character,Character> pMap = new HashMap<Character, Character>(){{
            put(']','[');
            put(')','(');
            put('}','{');
        }};
        for(int i=0;i<s.length();i++){
            Character c = s.charAt(i);
            if(c=='[' || c==']' || c=='(' || c==')' || c=='{' || c=='}'){
                if(pMap.containsKey(c)){
                    if(ps.size()!=0 && ps.get(ps.size()-1).equals(pMap.get(c))){
                        ps.remove(ps.size()-1);
                        continue;
                    }else{
                        return false;
                    }
                }else{
                    ps.add(c);
                }
            }else{
                continue;
            }
        }
        if(ps.size()==0){
            return true;
        }else{
            return false;
        }
    }
}
