package indi.gxwu.algorithm.assignment;

import java.util.Random;

/**
 * @author: gx.wu
 * @date: 2022/9/2
 * @description:
 **/
public class TestCase {

    public static void main(String[] args) {


//        int[][] weights = new int[][]{
////                {3, 5, 5, 4},
////                {2, 2, 0, 2},
////                {2, 4, 4, 0},
////                {0, 1, 0, 0},
//
//                {3	,5	,61	,15	,54	,60	,92	,8	,52	,70},
//                {86	,74	,17	,99	,18	,25	,93	,63	,9	,21},
//                {64	,3	,29	,58	,65	,58	,10	,16	,36	,2},
//                {93	,82	,34	,95	,3	,96	,76	,97	,3	,33},
//                {53	,57	,12	,87	,98	,41	,7	,31	,83	,58},
//                {6	,41	,45	,19	,29	,66	,91	,32	,50	,97},
//                {31	,65	,25	,91	,43	,43	,50	,31	,9	,79},
//                {7	,13	,46	,70	,30	,21	,37	,42	,49	,70},
//                {96	,75	,96	,54	,14	,76	,31	,62	,61	,84},
//                {79	,56	,50	,17	,59	,60	,31	,43	,3	,63},
//        };


        int row = 10;
        int col = 10;
        int[][] weights = new int[row][col];
        Random random = new Random();
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < row; j++) {
                weights[i][j] = random.nextInt(50);
            }
        }
        runTest(weights);
    }


    public static void runTest(int[][] weights){

        KuhnMunkresAlgorithm2 km = new KuhnMunkresAlgorithm2(weights, false, false);
        int[][] maxBipartie = km.getBipartie();
        km.printMaxBipartie(maxBipartie);

        System.out.println("================================================================");
        System.out.println("================================================================");
        System.out.println("================================================================");

        BruteForceCase forceCase = new BruteForceCase(weights, false);
        forceCase.handle();
    }
}
