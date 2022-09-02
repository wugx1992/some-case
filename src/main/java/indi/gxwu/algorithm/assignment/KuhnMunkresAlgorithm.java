package indi.gxwu.algorithm.assignment;

import java.util.Arrays;

/**
 * @author: gx.wu
 * @date: 2022/8/31
 * @description:
 * https://www.pudn.com/news/62615bed0e75e42012408798.html
 **/
public class KuhnMunkresAlgorithm {

    private int maxN, lenX, lenY;
    private int[][] weights;
    private boolean[] visitX, visitY;
    private int[] lx, ly;
    private int[] slack;
    private int[] matchYx;

    public KuhnMunkresAlgorithm(int[][] weight) {
        preProcess(weight);
        printWeightMatrix();
    }


    public static void main(String[] args) {
        int[][] weights = new int[][] {
//                {-1, -2, -3},
//                {-2, -4, -6},
//                {-3, -6, -9},
//                {-2, -999, -999},

                {3, 5, 5, 4},
                {2, 2, 0, 2},
                {2, 4, 4, 0},
                {0, 1, 0, 0},

//                {-3, -5, -5, -4},
//                {-2, -2, 0, -2},
//                {-2, -4, -4, 0},
//                {0, -1, 0, 0},
//        *3     5     5     4
//        2     2    *0     2
//        2     4     4    *0
//        0    *1     0     0

//                {3, 5, 4},
//                {2, 0, 2},
//                {2, 4, 0},

//                {3, 0, 4},
//                {2, 1, 3},
//                {0, 0, 5},
        };
        KuhnMunkresAlgorithm km = new KuhnMunkresAlgorithm(weights);
        int[][] maxBipartie = km.getMaxBipartie();
        km.printMaxBipartie(maxBipartie);


    }


    private void preProcess(int[][] weight) {
        lenX = weight.length;
        lenY = weight[0].length;
        maxN = Math.max(lenX, lenY);
        visitX = new boolean[maxN];
        visitY = new boolean[maxN];
        lx = new int[maxN];
        ly = new int[maxN];
        slack = new int[maxN];
        matchYx = new int[maxN];

        Arrays.fill(matchYx, -1);
        weights = new int[maxN][maxN];
        for (int i = 0; i < maxN; i++) {
            Arrays.fill(weights[i], 0);
        }
        for (int i = 0; i < lenX; i++) {
            for (int j = 0; j < lenY; j++) {
                weights[i][j] = weight[i][j];
            }
        }
    }


    public int[][] getMaxBipartie() {
        //initialize memo data for class
        //initialize label X and Y
        Arrays.fill(ly, 0);
        Arrays.fill(lx, 0);
        for (int i = 0; i < maxN; i++) {
            for (int j = 0; j < maxN; j++) {
                if (lx[i] < weights[i][j]) {
                    lx[i] = weights[i][j];
                }
            }
        }

        //find a match for each X point
        for (int u = 0; u < maxN; u++) {
            Arrays.fill(slack,  Integer.MAX_VALUE);
            while (true) {
                Arrays.fill(visitX, false);
                Arrays.fill(visitY, false);
                //if find it, go on to the next point
                if (findPath(u)) {
                    //printMatch();
                    break;
                }
                //otherwise update labels so that more edge will be added in
                int inc = Integer.MAX_VALUE;
                for (int v = 0; v < maxN; v++) {
                    if (!visitY[v] && slack[v] < inc) {
                        inc = slack[v];
                    }
                }
                for (int i = 0; i < maxN; i++) {
                    if (visitX[i]) {
                        lx[i] -= inc;
                    }
                    if (visitY[i]) {
                        ly[i] += inc;
                    }
                }
                printSlack();
                printVisit();
                printL();
            }
        }

        int totalWeight = 0;
        for (int i = 0; i < maxN; i++) {
            if (matchYx[i] >= 0) {
                totalWeight += weights[matchYx[i]][i];
            }
        }
        System.out.println("total weight：" + totalWeight);
        return matchResult();
    }



    private boolean findPath(int u) {
        visitX[u] = true;
        System.out.println("----------- x："+ u +" findPath check  -----------------");
        for (int v = 0; v < maxN; v++) {
            if (!visitY[v]) {
                //若 temp == 0，则为最大权匹配
                int temp = lx[u] + ly[v] - weights[u][v];
                if (temp == 0.0) {
                    visitY[v] = true;
                    if (matchYx[v] == -1 || findPath(matchYx[v])) {
                        matchYx[v] = u;
                        System.out.println("----------- x："+ u +" findPath true y：" + v + "  -----------------");
                        printMatch();
                        return true;
                    }
                } else {
                    slack[v] = Math.min(slack[v], temp);
                }
            }
        }
        System.out.println("----------- x："+ u +" findPath false  -----------------");
        printMatch();
        return false;
    }

    public int[][] matchResult() {
        int len = Math.min(lenX, lenY);
        int[][] res = new int[len][2];
        int count = 0;
        for (int i = 0; i < lenY; i++) {
            if (matchYx[i] >= 0 && matchYx[i] < lenX) {
                res[count][0] = matchYx[i];
                res[count++][1] = i;
            }
        }
        return res;
    }

    private void printMatch(){
        System.out.println("----------- y match -----------------");
        int length = matchYx.length;
        for(int c = 0; c < length; c++) {
            System.out.print(String.format("%1$6s", "Y"+c));
        }
        System.out.print("\n");

        for(int c = 0; c < length; c++) {
            System.out.print(String.format("%1$6s", matchYx[c]));
        }
        System.out.print("\n");

    }

    private void printL(){
        System.out.println("----------- lx value -----------------");
        int length = lx.length;
        for(int c = 0; c < length; c++) {
            System.out.print(String.format("%1$6s", lx[c]));
        }
        System.out.print("\n");
        System.out.println("----------- ly value -----------------");
        length = ly.length;
        for(int c = 0; c < length; c++) {
            System.out.print(String.format("%1$6s", ly[c]));
        }
        System.out.print("\n");

    }

    private void printVisit(){
        System.out.println("----------- visitX value -----------------");
        int length = visitX.length;
        for(int c = 0; c < length; c++) {
            System.out.print(String.format("%1$6s", visitX[c]));
        }
        System.out.print("\n");
        System.out.println("----------- visitY value -----------------");
        length = visitY.length;
        for(int c = 0; c < length; c++) {
            System.out.print(String.format("%1$6s", visitY[c]));
        }
        System.out.print("\n");

    }

    private void printSlack(){
        System.out.println("----------- slack value -----------------");
        int length = slack.length;
        for(int c = 0; c < length; c++) {
            System.out.print(slack[c]+" ");
        }
        System.out.print("\n");
    }


    private void printWeightMatrix(){
        System.out.println("----------- weight matrix -----------------");
        int row = weights.length;
        int col = weights[0].length;
        for(int r = 0; r < row; r++){
            for(int c = 0; c < col; c++) {
                System.out.print(String.format("%1$6s", weights[r][c]));
            }
            System.out.print("\n");
        }
    }

    public void printMaxBipartie(int[][] maxBipartie){
        System.out.println("----------- max bipartie -----------------");
        if(maxBipartie == null) {
            System.out.println("null");
            return;
        }
        int row = maxBipartie.length;
        for(int r = 0; r < row; r++){
            System.out.println(String.format("%1$6s", "X"+maxBipartie[r][0]) + String.format("%1$6s", "Y"+maxBipartie[r][1]));
        }

        System.out.println("----------- match matrix -----------------");
        row = weights.length;
        int col = weights[0].length;
        for(int r = 0; r < row; r++){
            int matchX = -1;
            int matchY = -1;
            for(int c = 0; c < col; c++) {
                for(int m = 0; m < maxBipartie.length; m ++) {
                    if(maxBipartie[m][0] == r) {
                        matchX = m;
                        matchY = maxBipartie[m][1];
                        break;
                    }
                }
                String desc = "";
                if(matchY == c) {
                    desc += "*";
                }
                desc += weights[r][c];
                System.out.print(String.format("%1$6s", desc));
            }
            System.out.print("\n");
        }


    }

}
