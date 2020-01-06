package indi.gxwu.other;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author: gx.wu
 * @Date: 2020/1/3 17:21
 * @Description: code something to describe this module what it is
 */
public class WordPatternTest {


    public static void main(String[] args) {
        WordPatternTest t = new WordPatternTest();
        String pattern = "abba";
        String teststr = "dog cat cat dog";
        System.out.println(t.wordPattern(pattern, teststr));

        pattern = "abba";
        teststr = "dog cat cat fish";
        System.out.println(t.wordPattern(pattern, teststr));
    }

    /**
     * @param pattern: a string, denote pattern string
     * @param teststr: a string, denote matching string
     * @return: an boolean, denote whether the pattern string and the matching string match or not
     */
    public boolean wordPattern(String pattern, String teststr) {
        // write your code here
        if(teststr == null || teststr.equals("")){
            if(pattern == null || pattern.equals("")){
                return true;
            }else {
                return false;
            }
        }
        String[] sp = teststr.split(" ");
        if(sp.length!=pattern.length()){
            return false;
        }
        Map<Character,String> map = new HashMap<Character, String>();
        Set<String> wordSet = new HashSet<String>();
        for(int i=0;i<pattern.length();i++){
            Character character = pattern.charAt(i);
            if(map.containsKey(character)){
                String word = map.get(character);
                if(word.equals(sp[i])){
                    continue;
                }else{
                    return false;
                }
            }else{
                if(wordSet.contains(sp[i])){
                    return false;
                }else{
                    map.put(character, sp[i]);
                    wordSet.add(sp[i]);
                }
            }
        }
        return true;
    }
}
