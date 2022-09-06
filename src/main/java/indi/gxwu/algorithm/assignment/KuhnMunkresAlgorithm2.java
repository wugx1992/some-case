package indi.gxwu.algorithm.assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: gx.wu
 * @date: 2022/8/31
 * @description:
 * https://www.pudn.com/news/62615bed0e75e42012408798.html
 * KM算法（通过遍历二部图中一边的顶点，冲突时调整顶点标杆的值，最后获得最佳匹配）
 * n x m 矩阵，两个顶点集合之间不存在的路径，可以用 0 表示，可以求解最大、最小权值匹配。
 * 不适用情况：如果 n > m 的情况无法被正常匹配，可以转换为 m x n 矩阵解决。
 **/
public class KuhnMunkresAlgorithm2 {

    private int maxN, lenX, lenY;
    private int[][] weights;
    private int[][] originalWeights;
    private boolean[] visitX, visitY;
    private int[] lx, ly;
    private int[] slack;
    private int[] matchYx;
    private boolean debugger = false;
    private boolean handleMaxCost = false;

    public KuhnMunkresAlgorithm2(int[][] weight, boolean handleMaxCost, boolean debugger) {
        this.debugger = debugger;
        this.handleMaxCost = handleMaxCost;
        preProcess(weight);
        printWeightMatrix();
    }


    public static void main(String[] args) {
        int[][] weights = new int[][] {
                //case 1、 x = y
//                {3, 5, 5, 4},
//                {2, 2, 0, 2},
//                {2, 4, 4, 0},
//                {0, 1, 0, 0},

                //case 2、  x < y
//                {3, 5, 5, 4, 3},
//                {2, 2, 0, 2, 5},
//                {2, 4, 4, 0, 7},
//                {0, 1, 0, 0, 3},

                //case 3、  x > y，有问题，最后一个x无法处理
//                {3, 5, 5, 4},
//                {2, 2, 0, 2},
//                {2, 4, 4, 0},
//                {0, 1, 0, 0},
//                {3, 6, 4, 2},

                //case 4、 x > y，转换为 y-x
                {3, 2, 2, 0, 3},
                {5, 2, 4, 1, 6},
                {5, 0, 4, 0, 4},
                {4, 2, 0, 0, 2},


        };
        KuhnMunkresAlgorithm2 km = new KuhnMunkresAlgorithm2(weights, true, true);
        AssignmentResult convertResult = km.getBipartie();

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
        originalWeights = new int[maxN][maxN];
        for (int i = 0; i < maxN; i++) {
            Arrays.fill(weights[i], 0);
            Arrays.fill(originalWeights[i], 0);
        }
        int maxItemVal = Integer.MIN_VALUE;
        for (int i = 0; i < lenX; i++) {
            for (int j = 0; j < lenY; j++) {
                originalWeights[i][j] = weight[i][j];
                if(handleMaxCost) {
                    weights[i][j] = weight[i][j];
                } else {
                    //不是求最大权重，则先取反
                    weights[i][j] = - weight[i][j];
                    if(maxItemVal < weight[i][j]) {
                        maxItemVal = weight[i][j];
                    }
                }
            }
        }
        if(!handleMaxCost) {
            for (int i = 0; i < lenX; i++) {
                for (int j = 0; j < lenY; j++) {
                    if(weights[i][j] != 0) {
                        weights[i][j] = maxItemVal*2 + weights[i][j];
                    }
                }
            }
        }
    }


    /**
     * 获取匹配的二部图
     * @return
     */
    public AssignmentResult getBipartie() {
        long time1 = System.currentTimeMillis();
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

        int totalLoopTimes = 0;
        //find a match for each X point
        for (int u = 0; u < maxN; u++) {
            //优化：不存在的节点直接跳过，避免造成死循环
            boolean allZero = true;
            for(int yi = 0; yi < maxN; yi++) {
                if(weights[u][yi] != 0) {
                    allZero = false;
                    break;
                }
            }
            if(allZero) {
                continue;
            }

            Arrays.fill(slack,  Integer.MAX_VALUE);
            int nodeLoopTimes = 0;
            while (true) {
                totalLoopTimes++;
                nodeLoopTimes++;
                printLog("######### node X：" + u + "，loop time：" + nodeLoopTimes + " #########\n");
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
                    if (!visitY[v] && slack[v] < inc && slack[v]!= Integer.MAX_VALUE) {
                        inc = slack[v];
                    }
                }

                //问题：无法获取可行顶标值
                if(inc == Integer.MAX_VALUE) {
                    System.out.println("======== 无法修改可行顶标值 =======");
                    break;
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
            printLog("######### total loop time：" + totalLoopTimes + " #########\n");

        }

        int[][] result = matchResult();
        if(debugger) {
            printBipartie(result);
            printMatch(result);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("耗时：" + (time2 - time1));
        return convertResult(result);
    }


    /**
     * 判断 x 节点是否存在匹配路径
     * @param nodeX
     * @return
     */
    private boolean findPath(int nodeX) {
        visitX[nodeX] = true;
        printLog("findPath check x："+ nodeX+"\n");
        for (int v = 0; v < maxN; v++) {
            //优化：在原来算法的基础上，对不存在的边进行过滤
            if (!visitY[v] && weights[nodeX][v] != 0) {
                //若 temp == 0，则为最大权匹配
                int temp = lx[nodeX] + ly[v] - weights[nodeX][v];
                if (temp == 0.0) {
                    visitY[v] = true;
                    printVisit();
                    if (matchYx[v] == -1 || findPath(matchYx[v])) {
                        matchYx[v] = nodeX;
                        printLog("findPath true x：" + nodeX + " y：" + v+"\n");
                        printMatchYX();
                        return true;
                    }
                } else {
                    slack[v] = Math.min(slack[v], temp);
                }
            }
        }
        printLog("findPath false x：" + nodeX+"\n");
        printVisit();
        printMatchYX();
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

    private void printMatchYX(){
        printLog("match-yx ");
        int length = matchYx.length;
        for(int c = 0; c < length; c++) {
            printLog(String.format("%1$6s", matchYx[c]));
        }
        printLog("\n");

    }

    private void printL(){
        printLog("lx ");
        int length = lx.length;
        for(int c = 0; c < length; c++) {
            printLog(String.format("%1$6s", lx[c]));
        }
        printLog("\n");
        printLog("ly ");
        length = ly.length;
        for(int c = 0; c < length; c++) {
            printLog(String.format("%1$6s", ly[c]));
        }
        printLog("\n");

    }

    private void printVisit(){
        printLog("visitX ");
        int length = visitX.length;
        for(int c = 0; c < length; c++) {
            printLog(String.format("%1$6s", visitX[c]));
        }
        printLog("\n");
        printLog("visitY ");
        length = visitY.length;
        for(int c = 0; c < length; c++) {
            printLog(String.format("%1$6s", visitY[c]));
        }
        printLog("\n");

    }

    private void printSlack(){
        printLog("slack ");
        int length = slack.length;
        for(int c = 0; c < length; c++) {
            printLog("      "+slack[c]);
        }
        printLog("\n");
    }


    private void printWeightMatrix(){
        printLog("----------- weight matrix -----------------\n");
        int row = weights.length;
        int col = weights[0].length;
        for(int r = 0; r < row; r++){
            for(int c = 0; c < col; c++) {
                printLog(String.format("%1$6s", originalWeights[r][c]));
            }
            printLog("\n");
        }
        if(!handleMaxCost) {
            printLog("----------- transform weight matrix -----------------\n");
            for(int r = 0; r < row; r++){
                for(int c = 0; c < col; c++) {
                    printLog(String.format("%1$6s", weights[r][c]));
                }
                printLog("\n");
            }
        }

    }

    public void printBipartie(int[][] bipartie){
        System.out.print("----------- bipartie -----------------\n");
        if(bipartie == null) {
            System.out.print("null\n");
            return;
        }
        int row = bipartie.length;
        for(int r = 0; r < row; r++){
            System.out.print(String.format("%1$6s", "X"+bipartie[r][0]) + String.format("%1$6s", "Y"+bipartie[r][1])+"\n");
        }
    }

    public void printMatch(int[][] bipartie){
        System.out.print("----------- match matrix -----------------\n");
        int row = originalWeights.length;
        int col = originalWeights[0].length;
        int totalWeight = 0;
        for(int r = 0; r < row; r++){
            int matchX = -1;
            int matchY = -1;
            for(int c = 0; c < col; c++) {
                for(int m = 0; m < bipartie.length; m ++) {
                    if(bipartie[m][0] == r) {
                        matchX = m;
                        matchY = bipartie[m][1];
                        break;
                    }
                }
                String desc = "";
                if(matchY == c) {
                    desc += "*";
                    totalWeight += originalWeights[r][c];
                }
                desc += originalWeights[r][c];
                System.out.print(String.format("%1$6s", desc));
            }
            System.out.print("\n");
        }

        System.out.println("total weight：" + totalWeight);
    }

    public AssignmentResult convertResult(int[][] bipartie){
        int row = originalWeights.length;
        int col = originalWeights[0].length;
        int totalWeight = 0;
        List<Integer> matchIndex = new ArrayList<>();
        for(int r = 0; r < row; r++){
            int matchY = -1;
            for(int c = 0; c < col; c++) {
                for(int m = 0; m < bipartie.length; m ++) {
                    if(bipartie[m][0] == r) {
                        matchY = bipartie[m][1];
                        break;
                    }
                }
                if(matchY == c) {
                    totalWeight += originalWeights[r][c];
                }
            }
            matchIndex.add(matchY);
        }

        AssignmentResult result = new AssignmentResult();
        result.setWeights(originalWeights);
        result.setTotalWeight(totalWeight);
        result.setMatchIndex(matchIndex);
        return result;
    }

    private void printLog(String message) {
        if(debugger) {
            System.out.print(message);
        }
    }

}
