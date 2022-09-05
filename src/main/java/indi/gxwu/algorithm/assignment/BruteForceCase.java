package indi.gxwu.algorithm.assignment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gx.wu
 * @date: 2022/9/2
 * @description:
 * 暴力破解匹配，获取匹配结果，校验算法的准确性
 **/
public class BruteForceCase {
    /**
     * 匹配结果，index = x，value = y
     */
    private List<List<Integer>> indexResults;
    /**
     * 倒数第二个元素代表匹配多少人，倒数第一个代表累加总权值
     */
    private List<List<Integer>> weightResults;
    private int[][] weights;
    private int lenX;
    private int lenY;
    private boolean debugger;


    public static void main(String[] args) {
        int[][] weights = new int[][]{
                {3, 5, 5, 4},
                {2, 2, 0, 2},
                {2, 4, 4, 0},
                {0, 1, 0, 0},
        };
        BruteForceCase forceCase = new BruteForceCase(weights, false);
        forceCase.handle();
    }


    BruteForceCase(int[][] weights, boolean debugger){
        this.debugger = debugger;
        this.weights = weights;
        indexResults = new ArrayList<>();
        weightResults = new ArrayList<>();
        lenX = weights.length;
        lenY = weights[0].length;
    }

    public void handle(){
        long time1 = System.currentTimeMillis();
        //获取所有组合顺序
        getValue(new ArrayList<>(), 0);

        //拼接结果
        int maxWeight = Integer.MIN_VALUE;
        int minWeight = Integer.MAX_VALUE;
        for(int row = 0; row < indexResults.size(); row ++) {
            List<Integer> index = indexResults.get(row);
            List<Integer> weight = new ArrayList<>();
            int totalWeight = 0;
            int personMatch = 0;
            for(int col = 0; col < index.size(); col++) {
                int targetWeight = weights[col][index.get(col)];
                weight.add(targetWeight);
                if(targetWeight != 0) {
                    totalWeight += targetWeight;
                    personMatch++;
                }
            }
            weight.add(personMatch);
            weight.add(totalWeight);
            weightResults.add(weight);
            if(maxWeight < totalWeight && personMatch == lenX) {
                maxWeight = totalWeight;
            }
            if(minWeight > totalWeight && personMatch == lenX) {
                minWeight = totalWeight;
            }
        }
        long time2 = System.currentTimeMillis();
        System.out.println("组合数量："+ indexResults.size() +"，耗时："+ (time2-time1));

        //打印输出
        for(int row = 0; row < indexResults.size(); row ++) {
            List<Integer> match = indexResults.get(row);
            List<Integer> weight = weightResults.get(row);
            int personMatch = weight.get(weight.size()-2);
            int totalWeight = weight.get(weight.size()-1);
            boolean matchMax = false;
            boolean matchMin = false;
            if(personMatch == lenX && totalWeight == maxWeight) {
                matchMax = true;
            }
            if(personMatch == lenX && totalWeight == minWeight) {
                matchMin = true;
            }
            if(!debugger && !matchMax && !matchMin) {
                continue;
            }

            System.out.print(String.format("%1$6s  ", row));

            System.out.print(" [");
            for(int ii = 0; ii < match.size(); ii++) {
                if(ii > 0) {
                    System.out.print(", ");
                }
                System.out.print(String.format("%1$3s", match.get(ii)));
            }
            System.out.print(" ]");

            System.out.print(" [");
            for(int ii = 0; ii < weight.size(); ii++) {
                if(ii > 0) {
                    System.out.print(", ");
                }
                System.out.print(String.format("%1$3s", weight.get(ii)));
            }
            System.out.print(" ]");
            if(personMatch == lenX && totalWeight == maxWeight) {
                System.out.print(" MAX");
            }
            if(personMatch == lenX && totalWeight == minWeight) {
                System.out.print(" MIN");
            }
            System.out.println();
        }
    }

    private void getValue(List<Integer> choiceY, int x){
        if(x >= lenX) {
            indexResults.add(choiceY);
            return;
        }
        for(int yi = 0; yi < lenY; yi ++) {
            if(choiceY.contains(yi)){
                continue;
            }
            List<Integer> currentChoice = new ArrayList<>(choiceY);
            currentChoice.add(yi);
            getValue(currentChoice, x+1);
        }
    }


}
