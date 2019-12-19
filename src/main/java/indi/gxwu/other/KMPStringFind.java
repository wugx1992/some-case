package indi.gxwu.other;

/**
 * @Author: gx.wu
 * @Date: 2019/12/19 10:19
 * @Description: code something to describe this module what it is
 */
public class KMPStringFind {


    public static void main(String[] args) {
        String str = "abcabaabcaca";
        String targetStr = "abaabcac";
        int[] nextArray = getNextArray(targetStr);
        for(int i=0;i<nextArray.length;i++) {
            System.out.print(nextArray[i]);
        }
        System.out.println();
        System.out.println(kmpSearch(str, targetStr));


    }

    public static int[] getNextArray(String str){
        int k=-1, j=0;
        int[] result = new int[str.length()];
        result[0] = -1;
        while (j<str.length()-1){
            if(k==-1 || str.charAt(j) == str.charAt(k)){
               k++;
               j++;
               result[j] = k;
            }else{
                k = result[k];
            }
        }
        return result;
    }

    public static int kmpSearch(String sourceStr, String targetStr){
        int[] nextArray = getNextArray(targetStr);
        int j = 0;
        int k = 0;
        while (j<sourceStr.length() && k<targetStr.length()){
            if(k==-1 || sourceStr.charAt(j) == targetStr.charAt(k)){
                j++;
                k++;
            }else{
                k = nextArray[k];
            }
        }
        if(k==targetStr.length()){
            return j-k;
        }else{
            return -1;
        }

    }
}
