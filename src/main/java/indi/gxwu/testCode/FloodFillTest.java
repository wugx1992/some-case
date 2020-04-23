package indi.gxwu.testCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: gx.wu
 * @Date: 2020/1/6 16:27
 * @Description: 洪水填充
 */
public class FloodFillTest {

    public static void main(String[] args) {
        FloodFillTest t = new FloodFillTest();
        int[][] image;
        int sr, sc, newColor;
        int result[][];

        image = new int[][]{{1,1,1},{1,1,0},{1,0,1}};
        sr = sc = 1;
        newColor = 2;
        t.print(image);
        result = t.floodFill(image, sr, sc, newColor);
        t.print(result);

        System.out.println("-------------------------");
        image = new int[][]{{0,0,0},{0,0,0}};
        sr = sc = 0;
        newColor = 2;
        t.print(image);
        result = t.floodFill(image, sr, sc, newColor);
        t.print(result);

    }

    public void print(int[][] image){
        for(int i=0;i<image.length;i++){
            System.out.printf("[");
            for(int j=0;j<image[i].length;j++){
                System.out.printf(image[i][j]+",");
            }
            System.out.printf("]\n");
        }
        System.out.println();
    }

    /**
     * @param image: a 2-D array
     * @param sr: an integer
     * @param sc: an integer
     * @param newColor: an integer
     * @return: the modified image
     */
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        // Write your code here
        List<List<Integer>> checks = new ArrayList<List<Integer>>();
        List<List<Integer>> imageValue = new ArrayList<List<Integer>>();
        for(int i=0;i<image.length;i++){
            List<Integer> check = new ArrayList<Integer>();
            List<Integer> value = new ArrayList<Integer>();
            for(int j=0;j<image[i].length;j++){
                check.add(0);
                value.add(image[i][j]);
            }
            checks.add(check);
            imageValue.add(value);
        }
        fill(imageValue, checks, image[sr][sc], sr, sc,newColor);
        for(int i=0;i<imageValue.size();i++){
            for(int j=0;j<imageValue.get(i).size();j++){
                image[i][j] = imageValue.get(i).get(j);
            }
        }
        return image;
    }

    public void fill(List<List<Integer>> values, List<List<Integer>> checks, int oldColor, int sr, int sc, int newColor){
        int targetValue = values.get(sr).get(sc);
        int check = checks.get(sr).get(sc);
        if(targetValue != oldColor || check==1){
            return;
        }
        values.get(sr).set(sc,newColor);
        checks.get(sr).set(sc,1);
        if(sr-1>=0) {
            fill(values, checks, oldColor, sr-1, sc, newColor);
        }
        if(sc-1>=0) {
            fill(values, checks, oldColor, sr, sc-1, newColor);
        }
        if(sc+1<values.get(sr).size()) {
            fill(values, checks, oldColor, sr, sc+1, newColor);
        }
        if(sr+1<values.size()) {
            fill(values, checks, oldColor, sr+1, sc, newColor);
        }
    }


}
