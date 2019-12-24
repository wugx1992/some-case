package indi.gxwu.other;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: gx.wu
 * @Date: 2019/12/20 9:44
 * @Description: 反转字符串
 */
public class ReverseWordsTest {

    public static void main(String[] args) {
        String str = "the  sky   is blue.";
        ReverseWordsTest t = new ReverseWordsTest();
        System.out.println(t.reverseWords(str));
        System.out.println(t.reverseWords2(str));
    }

    public String reverseWords2(String s){
        if(s==null){
            return "";
        }
        s = s.replaceAll("\\s+", " ");
        String[] sl = s.split(" ");
        StringBuffer buffer = new StringBuffer();
        for(int i=sl.length-1;i>=0;i--){
            buffer.append(sl[i]);
            if(i!=0){
                buffer.append(" ");
            }
        }
        return buffer.toString();
    }


    public String reverseWords(String s){
        if(s==null){
            return "";
        }
        String reg = "[a-zA-Z]+";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(s);
        List<String> rl = new ArrayList<String>();
        while (m.find()){
            rl.add(m.group());
        }
        StringBuffer buffer = new StringBuffer();
        for(int i=rl.size()-1;i>=0;i--){
            buffer.append(rl.get(i));
            if(i!=0){
                buffer.append(" ");
            }
        }
        return buffer.toString();
    }
}
