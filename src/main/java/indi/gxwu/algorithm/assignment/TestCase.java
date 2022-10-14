package indi.gxwu.algorithm.assignment;

import org.junit.Test;

import java.util.List;
import java.util.Random;

/**
 * @author: gx.wu
 * @date: 2022/9/2
 * @description:
 **/

public class TestCase {


    /**
     * 随机验证一个矩阵的分配问题
     */
    @Test
    public void verifyCorrectnessTest () {


//        int[][] weights = new int[][]{
////                {3, 5, 5, 4},
////                {2, 2, 0, 2},
////                {2, 4, 4, 0},
////                {0, 1, 0, 0},
//
////                {3	,5	,61	,15	,54	,60	,92	,8	,52	,70},
////                {86	,74	,17	,99	,18	,25	,93	,63	,9	,21},
////                {64	,3	,29	,58	,65	,58	,10	,16	,36	,2},
////                {93	,82	,34	,95	,3	,96	,76	,97	,3	,33},
////                {53	,57	,12	,87	,98	,41	,7	,31	,83	,58},
////                {6	,41	,45	,19	,29	,66	,91	,32	,50	,97},
////                {31	,65	,25	,91	,43	,43	,50	,31	,9	,79},
////                {7	,13	,46	,70	,30	,21	,37	,42	,49	,70},
////                {96	,75	,96	,54	,14	,76	,31	,62	,61	,84},
////                {79	,56	,50	,17	,59	,60	,31	,43	,3	,63},
//
//
//        };

        int row = 6;
        int col = 6;
        int[][] weights = generateMatrix(row, col);
        runAssignment(weights);
    }

    /**
     * 循环随机多个矩阵验证分配问题
     */
    @Test
    public void verifyCorrectnessLoopTest () {
        int row = 9;
        int col = 9;
        int loopTimes = 10;
        for(int t = 0; t < loopTimes; t++) {
            System.out.println("\n============================  "+(t+1)+"  ===============================");
            int[][] weights = generateMatrix(row, col);
            boolean verifyTrue = runAssignment(weights);
            if(!verifyTrue) {
                System.err.println("算法匹配错误！");
                break;
            }
        }
    }


    public static boolean runAssignment(int[][] weights){
        boolean handleMaxCost = false;
        System.out.println("================================================================");
        System.out.println("=========================     矩阵     ==========================");
        printMatrix(weights);

        System.out.println("================================================================");
        System.out.println("=================  遍历所有情况方式（暴力）  =======================");
        BruteForceCase forceCase = new BruteForceCase(weights, false);
        forceCase.handle();

        System.out.println("================================================================");
        System.out.println("===================      KuhnMunkres     =======================");
        KuhnMunkresAlgorithm2 kma = new KuhnMunkresAlgorithm2(weights, handleMaxCost, false);
        AssignmentResult kmaResult = kma.getBipartie();
        kmaResult.printMatchResult();
        boolean matchResult1 = forceCase.isMatchResult(kmaResult, handleMaxCost);
        if(matchResult1) {
            System.out.println("验证正确");
        }else{
            System.err.println("验证错误");
        }

        System.out.println("================================================================");
        System.out.println("=====================    Hungarian     =========================");
        HungarianAlgorithm2 ha = new HungarianAlgorithm2(weights, handleMaxCost, false);
        AssignmentResult haResult = ha.runMunkres();
        haResult.printMatchResult();
        boolean matchResult2 = forceCase.isMatchResult(haResult, handleMaxCost);
        if(matchResult2) {
            System.out.println("验证正确");
        }else{
            System.err.println("验证错误");
        }

        System.out.println("================================================================");
        System.out.println("=====================  Hungarian two   =========================");
        HungarianTwoAlgorithm2 hat = new HungarianTwoAlgorithm2(weights, handleMaxCost, false);
        List<AssignmentResult> hatResult = hat.runMunkres();
        boolean matchResult3 = true;
        if(hatResult.size()==0) {
            matchResult3 = false;
        }
        for(AssignmentResult assignment : hatResult){
            assignment.printMatchResult();
            boolean match = forceCase.isMatchResult(assignment, handleMaxCost);
            if(match) {
                System.out.println("验证正确");
            }else{
                System.err.println("验证错误");
                matchResult3 = false;
            }
            System.out.println("----------------------------------------------------------------");
        }


        return matchResult1 && matchResult2 && matchResult3;
    }

    private static int[][] generateMatrix(int row, int col){
        int[][] weights = new int[row][col];
        Random random = new Random();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                weights[i][j] = random.nextInt(10);
            }
        }
        //排除某一项任务，所有人都没有能力胜任（整列都为0）
        boolean available = true;
        for(int c = 0; c < col; c++) {
            boolean allZero = true;
            for(int r = 0; r < row; r++) {
                if(weights[r][c] != 0) {
                    allZero = false;
                    break;
                }
            }
            if(allZero) {
                available = false;
                break;
            }
        }
        if(!available) {
            return generateMatrix(row, col);
        }
        return weights;
    }

    public static void printMatrix(int weight[][]){
        int nRow = weight.length;
        int nCol = weight[0].length;
        for(int r = 0; r < nRow; r++){
            for(int c = 0; c < nCol; c++) {
                System.out.print(String.format("%1$6s", weight[r][c]));
            }
            System.out.print("\n");
        }
    }

}
