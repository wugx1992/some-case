package indi.gxwu.testCode;

/**
 * @Author: gx.wu
 * @Date: 2020/1/3 17:34
 * @Description: 给定Excel工作表中显示的列名称，返回其对应的列号。
 * A -> 1
 * B -> 2
 * C -> 3
 * ...
 * Z -> 26
 * AA -> 27
 * AB -> 28
 */
public class ExcelTitleToNumberTest {


    public static void main(String[] args) {
        ExcelTitleToNumberTest t = new ExcelTitleToNumberTest();
        String s = "A";
        System.out.println(t.titleToNumber(s));

        s = "B";
        System.out.println(t.titleToNumber(s));

        s = "AA";
        System.out.println(t.titleToNumber(s));

        s = "AB";
        System.out.println(t.titleToNumber(s));
    }


    /**
     * @param s: a string
     * @return: return a integer
     */
    public int titleToNumber(String s) {
        // write your code here
        int aValue = 1;
        Character aChar = 'A';
        int total = 0;
        for(int i=0;i<s.length();i++){
            Character charValue = s.charAt(i);
            int t = s.length()-i-1;
            int v = (charValue-aChar+aValue);
            total += Math.pow(26,t)*v;
        }
        return total;
    }
}
