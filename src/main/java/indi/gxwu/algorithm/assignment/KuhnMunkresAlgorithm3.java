//package indi.gxwu.algorithm.assignment;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author: gx.wu
// * @date: 2022/8/31
// * @description:
// * KM算法（通过遍历二部图中一边的顶点，冲突时调整顶点标杆的值，最后获得最佳匹配）
// * 可能存在权值相同的路径，尝试将其都找出来。
// * n x m 矩阵，两个顶点集合之间不存在的路径，可以用 0 表示，可以求解最大、最小权值匹配。
// * 不适用情况：如果 n > m 的情况无法被正常匹配，可以转换为 m x n 矩阵解决。
// **/
//public class KuhnMunkresAlgorithm3 {
//
//    private int maxN, lenX, lenY;
//    private int[][] weights;
//    private int[][] originalWeights;
//    private boolean[] visitX, visitY;
//    private int[] lx, ly;
//    private int[] slack;
//    private int[] matchYx;
//    private boolean debugger = false;
//    private boolean handleMaxCost = false;
//    private List<List<Integer>> allMatchYx;
//
//    public KuhnMunkresImplementTwo3(int[][] weight, boolean handleMaxCost, boolean debugger) {
//        this.debugger = debugger;
//        this.handleMaxCost = handleMaxCost;
//        this.allMatchYx = new ArrayList<>();
//        preProcess(weight);
//        printWeightMatrix();
//    }
//
//
//    public static void main(String[] args) {
//        int[][] weights = new int[][] {
//                //case 1、 x = y
////                {3, 5, 5, 4},
////                {2, 2, 0, 2},
////                {2, 4, 4, 0},
////                {0, 1, 0, 0},
//
//                //case 2、  x < y
////                {3, 5, 5, 4, 3},
////                {2, 2, 0, 2, 5},
////                {2, 4, 4, 0, 7},
////                {0, 1, 0, 0, 3},
//
//                //case 3、  x > y，有问题，最后一个x无法处理
////                {3, 5, 5, 4},
////                {2, 2, 0, 2},
////                {2, 4, 4, 0},
////                {0, 1, 0, 0},
////                {3, 6, 4, 2},
//
//                //case 4、 x > y，转换为 y-x
////                {3, 2, 2, 0, 3},
////                {5, 2, 4, 1, 6},
////                {5, 0, 4, 0, 4},
////                {4, 2, 0, 0, 2},
//
//                {4,     6,     8,     3 },
//                {1,     5,     2,     3 },
//                {6,     6,     8,     0 },
//                {9,     8,     7,     6 },
//        };
//        /**
//         *     11   [  1,   3,   2,   0 ] [  6,   3,   8,   9,   4,  26 ] MAX
//         *     17   [  2,   3,   1,   0 ] [  8,   3,   6,   9,   4,  26 ] MAX
//         *     18   [  3,   0,   1,   2 ] [  3,   1,   6,   7,   4,  17 ] MIN
//         */
//        KuhnMunkresAlgorithm3 km = new KuhnMunkresAlgorithm3(weights, true, true);
//        List<List<Integer>> bipartieResult = km.getBipartie();
//        km.printBipartie(bipartieResult);
//
//    }
//
//
//    private void preProcess(int[][] weight) {
//        lenX = weight.length;
//        lenY = weight[0].length;
//        maxN = Math.max(lenX, lenY);
//        visitX = new boolean[maxN];
//        visitY = new boolean[maxN];
//        lx = new int[maxN];
//        ly = new int[maxN];
//        slack = new int[maxN];
//        matchYx = new int[maxN];
//
//        Arrays.fill(matchYx, -1);
//        weights = new int[maxN][maxN];
//        originalWeights = new int[maxN][maxN];
//        for (int i = 0; i < maxN; i++) {
//            Arrays.fill(weights[i], 0);
//            Arrays.fill(originalWeights[i], 0);
//        }
//        int maxItemVal = Integer.MIN_VALUE;
//        for (int i = 0; i < lenX; i++) {
//            for (int j = 0; j < lenY; j++) {
//                originalWeights[i][j] = weight[i][j];
//                if(handleMaxCost) {
//                    weights[i][j] = weight[i][j];
//                } else {
//                    //不是求最大权重，则先取反
//                    weights[i][j] = - weight[i][j];
//                    if(maxItemVal < weight[i][j]) {
//                        maxItemVal = weight[i][j];
//                    }
//                }
//            }
//        }
//        if(!handleMaxCost) {
//            for (int i = 0; i < lenX; i++) {
//                for (int j = 0; j < lenY; j++) {
//                    if(weights[i][j] != 0) {
//                        weights[i][j] = maxItemVal*2 + weights[i][j];
//                    }
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 获取匹配的二部图
//     * @return
//     */
//    public List<List<Integer>> getBipartie() {
//        long time1 = System.currentTimeMillis();
//        //initialize memo data for class
//        //initialize label X and Y
//        Arrays.fill(ly, 0);
//        Arrays.fill(lx, 0);
//        for (int i = 0; i < maxN; i++) {
//            for (int j = 0; j < maxN; j++) {
//                if (lx[i] < weights[i][j]) {
//                    lx[i] = weights[i][j];
//                }
//            }
//        }
//
//        int totalLoopTimes = 0;
//        //find a match for each X point
//        for (int xi = 0; xi < maxN; xi++) {
//            //优化：不存在的节点直接跳过，避免造成死循环
//            boolean allZero = true;
//            for(int yi = 0; yi < maxN; yi++) {
//                if(weights[xi][yi] != 0) {
//                    allZero = false;
//                    break;
//                }
//            }
//            if(allZero) {
//                continue;
//            }
//
//            Arrays.fill(slack,  Integer.MAX_VALUE);
//            int nodeLoopTimes = 0;
//            while (true) {
//                totalLoopTimes++;
//                nodeLoopTimes++;
//                printLog("######### node X：" + xi + "，loop time：" + nodeLoopTimes + " #########\n");
//                Arrays.fill(visitX, false);
//                Arrays.fill(visitY, false);
//                /**
//                 * 1、如果找到匹配路径（且非最后一个节点），则跳转下一个节点；
//                 * 2、如果是最后一个节点，即使路径也匹配，也继续循环，尝试查找出其他匹配路径
//                 */
//                if (findPath(xi)) {
//                    if(xi != (maxN-1)) {
//                        //非最后一个节点，跳出循环，继续下一个节点
//                        break;
//                    }else{
//                        //最后一个节点，记录当前匹配路径节点，尝试继续查找其他匹配路径
//                        List<Integer> currentMatchResult = Arrays.stream(matchYx).boxed().collect(Collectors.toList());
//                        allMatchYx.add(currentMatchResult);
//                    }
//                }
//                //otherwise update labels so that more edge will be added in
//                int inc = Integer.MAX_VALUE;
//                for (int v = 0; v < maxN; v++) {
//                    if (!visitY[v] && slack[v] < inc && slack[v]!= Integer.MAX_VALUE) {
//                        inc = slack[v];
//                    }
//                }
//
//                //问题：无法获取可行顶标值
//                if(inc == Integer.MAX_VALUE) {
//                    System.out.println("======== 无法修改可行顶标值 =======");
//                    break;
//                }
//
//                for (int i = 0; i < maxN; i++) {
//                    if (visitX[i]) {
//                        lx[i] -= inc;
//                    }
//                    if (visitY[i]) {
//                        ly[i] += inc;
//                    }
//                }
//                printSlack();
//                printVisit();
//                printL();
//            }
//            printLog("######### total loop time：" + totalLoopTimes + " #########\n");
//
//        }
//
//        long time2 = System.currentTimeMillis();
//        List<List<Integer>> result = matchResult();
//        System.out.println("耗时：" + (time2 - time1));
//        return result;
//    }
//
//
//    /**
//     * 判断 x 节点是否存在匹配路径
//     * @param nodeX
//     * @return
//     */
//    private boolean findPath(int nodeX) {
//        visitX[nodeX] = true;
//        printLog("findPath check x："+ nodeX+"\n");
//        for (int v = 0; v < maxN; v++) {
//            //优化：在原来算法的基础上，对不存在的边进行过滤
//            if (!visitY[v] && weights[nodeX][v] != 0) {
//                //若 temp == 0，则为最大权匹配
//                int temp = lx[nodeX] + ly[v] - weights[nodeX][v];
//                if (temp == 0.0) {
//                    visitY[v] = true;
//                    printVisit();
//                    if (matchYx[v] == -1 || findPath(matchYx[v])) {
//                        matchYx[v] = nodeX;
//                        printLog("findPath true x：" + nodeX + " y：" + v+"\n");
//                        printMatch();
//                        return true;
//                    }
//                } else {
//                    slack[v] = Math.min(slack[v], temp);
//                }
//            }
//        }
//        printLog("findPath false x：" + nodeX+"\n");
//        printVisit();
//        printMatch();
//        return false;
//    }
//
//    public List<List<Integer>> matchResult() {
//        List<List<Integer>> result = new ArrayList<>();
//        for(int rs = 0; rs < allMatchYx.size(); rs++) {
//            List<Integer> matchUnit = new ArrayList<>();
//            List<Integer> currentMatchYx = allMatchYx.get(rs);
//            for (int i = 0; i < lenY; i++) {
//                if (currentMatchYx.get(i) >= 0 && currentMatchYx.get(i) < lenX) {
//                    matchUnit.add(currentMatchYx.get(i));
//                }
//            }
//            result.add(matchUnit);
//        }
//
//        return result;
//    }
//
//    private void printMatch(){
//        printLog("match-yx ");
//        int length = matchYx.length;
//        for(int c = 0; c < length; c++) {
//            printLog(String.format("%1$6s", matchYx[c]));
//        }
//        printLog("\n");
//
//    }
//
//    private void printL(){
//        printLog("lx ");
//        int length = lx.length;
//        for(int c = 0; c < length; c++) {
//            printLog(String.format("%1$6s", lx[c]));
//        }
//        printLog("\n");
//        printLog("ly ");
//        length = ly.length;
//        for(int c = 0; c < length; c++) {
//            printLog(String.format("%1$6s", ly[c]));
//        }
//        printLog("\n");
//
//    }
//
//    private void printVisit(){
//        printLog("visitX ");
//        int length = visitX.length;
//        for(int c = 0; c < length; c++) {
//            printLog(String.format("%1$6s", visitX[c]));
//        }
//        printLog("\n");
//        printLog("visitY ");
//        length = visitY.length;
//        for(int c = 0; c < length; c++) {
//            printLog(String.format("%1$6s", visitY[c]));
//        }
//        printLog("\n");
//
//    }
//
//    private void printSlack(){
//        printLog("slack ");
//        int length = slack.length;
//        for(int c = 0; c < length; c++) {
//            printLog("      "+slack[c]);
//        }
//        printLog("\n");
//    }
//
//
//    private void printWeightMatrix(){
//        System.out.println("----------- weight matrix -----------------");
//        int row = weights.length;
//        int col = weights[0].length;
//        for(int r = 0; r < row; r++){
//            for(int c = 0; c < col; c++) {
//                System.out.print(String.format("%1$6s", originalWeights[r][c]));
//            }
//            System.out.print("\n");
//        }
//        if(!handleMaxCost) {
//            System.out.println("----------- transform weight matrix -----------------");
//            for(int r = 0; r < row; r++){
//                for(int c = 0; c < col; c++) {
//                    System.out.print(String.format("%1$6s", weights[r][c]));
//                }
//                System.out.print("\n");
//            }
//        }
//
//    }
//
//    public void printBipartie(List<List<Integer>> bipartieResult){
//        System.out.println("----------- bipartie(" + bipartieResult.size() + ") -----------------");
//        if(bipartieResult == null || bipartieResult.size() == 0) {
//            System.out.println("empty!");
//            return;
//        }
//
//        for(int rs = 0; rs < bipartieResult.size(); rs++) {
//            System.out.println("### match result " + (rs+1)+" ###");
//            List<Integer> bipartie = bipartieResult.get(rs);
//            int matchLength = bipartie.size();
//            int totalWeight = 0;
//            for (int yi = 0; yi < matchLength; yi++) {
//                int xi = bipartie.get(yi);
//                if (xi >= 0) {
//                    totalWeight += originalWeights[xi][yi];
//                }
//                System.out.println(String.format("%1$6s", "X"+xi) + String.format("%1$6s", "Y"+yi));
//            }
//            System.out.println("total weight：" + totalWeight);
//
//            System.out.println("----------- matrix -----------------");
//            int row = originalWeights.length;
//            int col = originalWeights[0].length;
//            for(int r = 0; r < row; r++){
//                int matchX = -1;
//                int matchY = -1;
//                for(int c = 0; c < col; c++) {
//                    for(int m = 0; m < matchLength; m ++) {
//                        if(bipartie.get(m) == r) {
//                            matchX = bipartie.get(m);
//                            matchY = m;
//                            break;
//                        }
//                    }
//                    String desc = "";
//                    if(matchY == c) {
//                        desc += "*";
//                    }
//                    desc += originalWeights[r][c];
//                    System.out.print(String.format("%1$6s", desc));
//                }
//                System.out.print("\n");
//            }
//        }
//
//
//    }
//
//    private void printLog(String message) {
//        if(debugger) {
//            System.out.print(message);
//        }
//    }
//
//}
