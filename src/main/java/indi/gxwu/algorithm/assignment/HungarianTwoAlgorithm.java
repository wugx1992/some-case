package indi.gxwu.algorithm.assignment;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: gx.wu
 * @date: 2022/8/29
 * @description:
 * https://www.hungarianalgorithm.com/hungarianalgorithm.php
 * 匈牙利算法（通过寻找增广路径，之后反向选取饱和点的路径，获得最佳匹配）
 * n x m 矩阵，不要求 n = m，求解最小权重（求最大权重，可以将所有原权重取反，减去其中最小值）
 * 不适用情况：n x m 对应过来的二分图，必须是每个节点都有路径存在，比如 N0 必须 有 m 条连接到各个 M 节点 M0、M1、M2...Mm
 * n x m 中不存在的路径，不能用 0 或最大值来代表，不存在的路径都有可能被分配
 **/
public class HungarianTwoAlgorithm {

    private int nRow;
    private int nCol;
    private int[][] costs;
    private int[][] costsBackup;
    private int[][] masks;
    private int[] rowCover;
    private int[] colCover;
    private boolean debugger = false;
    private List<MatrixItem> paths = new ArrayList<>();
    private boolean handleMaxCost = false;
    private static final int STEP_ONE = 1;
    private static final int STEP_TWO = 2;
    private static final int STEP_THREE = 3;
    private static final int STEP_FOUR = 4;
    private static final int STEP_FIVE = 5;
    private static final int UNCOVERED = 0;
    private static final int COVERED = 1;
    private static final int STARRED_ZERO = 1;
    private static final int PRIMED_ZERO = 2;

    @Data
    public static class MatrixItem {
        private int row;
        private int col;
    }

    public HungarianTwoAlgorithm(int[][] costsParam, boolean handleMaxCost, boolean debugger){
        this.handleMaxCost = handleMaxCost;
        this.debugger = debugger;
        nRow = costsParam.length;
        nCol = costsParam[0].length;
        masks = new int[nRow][nCol];
        costs = new int[nRow][nCol];
        costsBackup = new int[nRow][nCol];
        rowCover = new int[nRow];
        colCover = new int[nCol];
        int maxValue = Integer.MIN_VALUE;
        for(int r = 0; r < nRow; r++) {
            for(int c = 0; c < nCol; c++) {
                costs[r][c] = costsParam[r][c];
                costsBackup[r][c] = costsParam[r][c];
                //将不存在的边（0），修改Integer.MAX_VALUE
                if(costsParam[r][c] == 0) {
                    costs[r][c] = Integer.MAX_VALUE;
                }
                if(maxValue < costsParam[r][c]) {
                    maxValue = costsParam[r][c];
                }
            }
        }
        //求取最大权值，将权值取反，再加上最大值
        if(handleMaxCost) {
            for(int r = 0; r < nRow; r++) {
                for(int c = 0; c < nCol; c++) {
                    if(costsParam[r][c] == 0) {
                        continue;
                    }
                    costs[r][c] = maxValue - costsParam[r][c];
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] costs = new int[][]{
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

                //case 3、 x > y
//                {3, 5, 5, 4},
//                {2, 2, 0, 2},
//                {2, 4, 4, 0},
//                {0, 1, 0, 0},
//                {3, 6, 4, 2},

//                {3, 5, 5, 4},
//                {2, 2, 0, 2},
//                {2, 4, 4, 0},
//                {0, 1, 0, 0},


//                { 52,    49,    26,    36,     3,    53,    62,    15,    93 },
//                { 16,    90,    31,    94,     4,    76,    45,    14,    77 },
//                { 17,    89,    13,    32,    43,    41,    95,     6,    35 },
//                { 19,    86,    64,     4,    60,    93,    15,    71,    68 },
//                { 37,    31,    79,    90,    47,    10,    38,    48,    81 },
//                { 42,    77,    35,    84,    47,    28,    83,    28,    53 },
//                { 29,    30,    22,    82,    74,     4,    58,    85,     7 },
//                { 47,     4,    78,    46,    80,     9,    24,    51,    22 },
//                { 28,    94,    20,    46,    80,     6,    86,    49,    98 },
////        3242   [  0,   1,   6,   5,   2,   3,   7,   4,   8 ] [ 52,  90,  95,  93,  79,  84,  85,  80,  98,   9, 756 ] MAX
////        162184   [  4,   0,   2,   3,   6,   7,   8,   1,   5 ] [  3,  16,  13,   4,  38,  28,   7,   4,   6,   9, 119 ] MIN
////        165202   [  4,   0,   7,   3,   6,   2,   8,   1,   5 ] [  3,  16,   6,   4,  38,  35,   7,   4,   6,   9, 119 ] MIN

//                {0,     1,     7,     4,     0 },
//                {7,     6,     5,     2,     3 },
//                {7,     5,     6,     6,     8 },
//                {8,     8,     3,     4,     8 },
//                {6,     1,     9,     6,     7 },
////        52   [  2,   0,   4,   1,   3 ] [  7,   7,   8,   8,   6,   5,  36 ] MAX
////        76   [  3,   0,   4,   1,   2 ] [  4,   7,   8,   8,   9,   5,  36 ] MAX
////        91   [  3,   4,   0,   2,   1 ] [  4,   3,   7,   3,   1,   5,  18 ] MIN
        };
        HungarianTwoAlgorithm hungarian = new HungarianTwoAlgorithm(costs, false, true);
        hungarian.printCostMatrix();
        AssignmentResult result = hungarian.runMunkres();
        result.printMatchResult();
        result.printMatchMatrix();

    }

    public AssignmentResult runMunkres(){
        long t1 = System.currentTimeMillis();
        boolean done = false;
        int step = 1;
        printLog("----------- START -----------------\n");
        int loop = 1;
        while (!done) {
            printLog("loop：" + loop + "   step："+step+"\n");
            switch (step){
                case STEP_ONE:
                    step = stepOne();
                    break;
                case STEP_TWO:
                    step = stepTwo();
                    break;
                case STEP_THREE:
                    step = stepThree();
                    break;
                case STEP_FOUR:
                    step = stepFour();
                    break;
                case STEP_FIVE:
                    done = true;
                    break;
            }
            if(debugger) {
                printCostMatrix();
                printMaskMatrix();
                printCover();
            }
            loop++;
        }
        long t2 = System.currentTimeMillis();
        System.out.println("耗时：" + (t2 -t1));
        return convertResult();
    }

    /**
     * Subtract row minima
     * For each row, find the lowest element and subtract it from each element in that row.
     * 每行减去最小值
     * @return
     */
    private int stepOne(){
        int minCost, col;
        for(int r = 0; r < nRow; r++) {
            minCost = costs[r][0];
            col = 0;
            //求该行最小元素
            for(int c = 0; c < nCol; c++) {
                if(costs[r][c] < minCost) {
                    minCost = costs[r][c];
                    col = c;
                }
            }
            printLog("当前行最小值：" + minCost +" 所在行/列：" + r +" / " + col+"\n");
            //该行所有元素减去最小值，使每行都至少有一个0元素
            for(int c = 0; c < nCol; c++) {
                if(costs[r][c] == Integer.MAX_VALUE) {
                    continue;
                }
                costs[r][c] -= minCost;
            }
        }
        return STEP_TWO;
    }

    /**
     * Subtract column minima
     * Similarly, for each column, find the lowest element and subtract it from each element in that column.
     * 每列减去最小值
     * @return
     */
    private int stepTwo(){
        int minCost, row;
        for(int c = 0; c < nCol; c++) {
            row = 0;
            minCost = costs[row][c];
            //求该行最小元素
            for(int r = 0; r < nRow; r++) {
                if(costs[r][c] < minCost) {
                    minCost = costs[r][c];
                    row = r;
                }
            }
            printLog("当前列最小值：" + minCost +" 所在行/列：" + row +" / " + c+"\n");
            //该列所有元素减去最小值，使每列都至少有一个0元素
            for(int r = 0; r < nRow; r++) {
                if(costs[r][c] == Integer.MAX_VALUE) {
                    continue;
                }
                costs[r][c] -= minCost;
            }
        }
        return STEP_THREE;
    }

    /**
     * Cover all zeros with a minimum number of lines
     * Cover all zeros in the resulting matrix using a minimum number of horizontal and vertical lines.
     * If n lines are required, an optimal assignment exists among the zeros. The algorithm stops.
     * If less than n lines are required, continue with Step 4.
     * 使用最少的线覆盖所有的0
     * @return
     */
    private int stepThree(){
        //重置覆盖行、列标识
        clearCovers();
        //重置掩码矩阵
        clearMask();
        //变换掩码矩阵
        transformMask();
        //打钩行
        List<Integer> tickRow = new ArrayList<>();
        //打钩列
        List<Integer> tickCol = new ArrayList<>();
        //遍历掩码矩阵，查找行不存在星0，打钩
        for(int r = 0; r < nRow; r++) {
            boolean hasStarZero = false;
            for(int c = 0; c < nCol; c++) {
                if(masks[r][c] == STARRED_ZERO) {
                    hasStarZero = true;
                    break;
                }
            }
            if(!hasStarZero) {
                tickRow.add(r);
            }
        }
        //遍历打钩行，查找存在划线0的列，打钩
        for(int i = 0; i < tickRow.size(); i++) {
            for(int c = 0; c < nCol; c++) {
                if(masks[tickRow.get(i)][c] == PRIMED_ZERO) {
                    if(!tickCol.contains(c)) {
                        tickCol.add(c);
                    }
                }
            }
        }
        //遍历打钩列，寻找存在星0的行，打钩
        for(int i = 0; i < tickCol.size(); i++) {
            for(int r = 0; r < nRow; r++) {
                if(masks[r][tickCol.get(i)] == STARRED_ZERO) {
                    if(!tickRow.contains(r)) {
                        tickRow.add(r);
                    }
                }
            }
        }


        /**
         * 尝试寻找最优解（覆盖0线）
         * 线路数量计算规则：
         * 1、未被打钩的行，算一条线
         * 2、已被打钩的列，算一条线
         */
        int countLines = (nRow - tickRow.size()) + tickCol.size();
        for(int r = 0; r < nRow; r++) {
            //未被打钩的行，算一条线
            if(!tickRow.contains(r)) {
                rowCover[r] = COVERED;
            }
        }
        for(int c = 0; c < nCol; c++) {
            //已被打钩的列，算一条线
            if(tickCol.contains(c)) {
                colCover[c] = COVERED;
            }
        }
        printLog("覆盖0的最小线路条数：" + countLines+"\n");
        //覆盖0的线数大于等于矩阵行数、或列数，代表可以结束了
        if(countLines >= nCol || countLines >= nRow) {
            return STEP_FIVE;
        }else {
            return STEP_FOUR;
        }
    }

    /**
     * Create additional zeros
     * Find the smallest element (call it k) that is not covered by a line in Step 3.
     * Subtract k from all uncovered elements, and add k to all elements that are covered twice.
     * @return
     */
    private int stepFour(){
        int minCost = findSmallest();
        printLog("未被覆盖的行、列中最小元素为：" + minCost + "\n");
        for(int r = 0; r < nRow; r++) {
            for(int c = 0; c < nCol; c++) {
                if(costs[r][c] == Integer.MAX_VALUE) {
                    continue;
                }
                if(rowCover[r] == UNCOVERED && colCover[c] == UNCOVERED) {
                    costs[r][c] -= minCost;
                }else if(rowCover[r] == COVERED && colCover[c] == COVERED){
                    costs[r][c] += minCost;
                }
            }
        }
        return STEP_THREE;
    }

    /**
     * 变换掩码矩阵，为寻找最少线路覆盖0做准备
     */
    private void transformMask(){
        /**
         * 1、将独立0元素标记为星0。
         * a.行只有一个未覆盖0，标记为星0，所在列其他未覆盖0标记为划线0；
         * b.列只有一个未覆盖0，标记为星0，所在行其他未覆盖0标记为划线0；
         */
        for(int r = 0; r < nRow; r++) {
            for(int c = 0; c < nCol; c++) {
                //元素为0，未被被覆盖
                if(costs[r][c] == 0 && masks[r][c] == UNCOVERED) {
                    int zeroSizeForRow = countUnCoverZeroInRow(r);
                    int zeroSizeForCol = countUnCoverZeroInCol(c);
                    //行唯一0时，除了自身标记为星0外，当前列其他0元素标记为划线0
                    if(zeroSizeForRow == 1) {
                        if(zeroSizeForCol > 1) {
                            fillColPrimedZeroExcludeIndex(r, c);
                        }
                        masks[r][c] = STARRED_ZERO;
                    }
                    //列唯一0时，除了自身标记为星0外，当前行其他0元素标记为划线0
                    else if(zeroSizeForCol ==1) {
                        if(zeroSizeForRow > 1) {
                            fillRowPrimedZeroExcludeIndex(r, c);
                        }
                        masks[r][c] = STARRED_ZERO;
                    }
                }
            }
        }
        /**
         * 2、是否仍然有未被覆盖的0元素，寻找行和列最少未被覆盖的位置，标记为星0，其所在行和列其他未被覆盖0元素标记为划线0
         */
        int leastUnCoverZeroRowIndex  = findRowLeastUnCoverZero();
        //整个矩阵已经不存在未被覆盖的0，结束转换
        if(leastUnCoverZeroRowIndex == -1) {
            return ;
        }

        if(leastUnCoverZeroRowIndex >=0) {
            int leastUnCoverZeroColIndex = -1;
            int leastCount = Integer.MAX_VALUE;
            for(int c = 0; c < nCol; c++) {
                //元素为0，未被被覆盖
                if(costs[leastUnCoverZeroRowIndex][c] == 0 && masks[leastUnCoverZeroRowIndex][c] == UNCOVERED) {
                    int zeroSizeForCol = countUnCoverZeroInCol(c);
                    if(zeroSizeForCol > 0 && leastCount > zeroSizeForCol) {
                        leastUnCoverZeroColIndex = c;
                        leastCount = zeroSizeForCol;
                    }
                }
            }
            if(leastUnCoverZeroColIndex >= 0) {
                fillRowPrimedZeroExcludeIndex(leastUnCoverZeroRowIndex, leastUnCoverZeroColIndex);
                fillColPrimedZeroExcludeIndex(leastUnCoverZeroRowIndex, leastUnCoverZeroColIndex);
                masks[leastUnCoverZeroRowIndex][leastUnCoverZeroColIndex] = STARRED_ZERO;
            }
        }
        //递归调用，处理剩余的元素
        transformMask();
    }

    /**
     * 计数指定行未被覆盖的0个数
     * @param row
     * @return
     */
    private int countUnCoverZeroInRow(int row){
        int count = 0;
        for(int c = 0; c < nCol; c++) {
            if(costs[row][c] != 0) {
                continue;
            }
            if(masks[row][c] != UNCOVERED) {
                continue;
            }
            count ++;
        }
        return count;
    }


    /**
     * 计数指定列未被覆盖的0个数
     * @param col
     * @return
     */
    private int countUnCoverZeroInCol(int col){
        int count = 0;
        for(int r = 0; r < nRow; r++) {
            if(costs[r][col] != 0) {
                continue;
            }
            if(masks[r][col] != UNCOVERED) {
                continue;
            }
            count ++;
        }
        return count;
    }

    /**
     * 查找指定行未被覆盖的0位置
     * @param row
     * @return
     */
    private List<Integer> findRowUnCoverZero(int row){
        List<Integer> colIndex = new ArrayList<>();
        for(int c = 0; c < nCol; c++) {
            if(costs[row][c] != 0) {
                continue;
            }
            if(masks[row][c] == COVERED) {
                continue;
            }
            colIndex.add(c);
        }
        return colIndex;
    }

    /**
     * 查找最少未覆盖0所在行
     * @return
     */
    private int findRowLeastUnCoverZero() {
        int leastCount = Integer.MAX_VALUE;
        int index = -1;
        for(int r = 0; r < nRow; r++) {
            int zeroSize = countUnCoverZeroInRow(r);
            if(zeroSize > 0 && leastCount > zeroSize) {
                leastCount = zeroSize;
                index = r;
            }
        }
        return index;
    }


    /**
     * 清空行、列覆盖标记
     */
    private void clearCovers(){
        Arrays.fill(rowCover, UNCOVERED);
        Arrays.fill(colCover, UNCOVERED);
    }

    /**
     * 重置掩码矩阵
     */
    private void clearMask(){
        for(int r = 0; r < nRow; r++) {
            Arrays.fill(masks[r], UNCOVERED);
        }
    }


    /**
     * 从未覆盖的行、列元素中，找出最小的元素
     * @return
     */
    private int findSmallest(){
        int minVal = Integer.MAX_VALUE;
        for(int r = 0; r < nRow; r++) {
            for(int c = 0; c < nCol; c++) {
                if(rowCover[r] == UNCOVERED && colCover[c] == UNCOVERED) {
                    if(minVal > costs[r][c]) {
                        minVal = costs[r][c];
                    }
                }
            }
        }
        return minVal;
    }

    /**
     * 除了节点（row,col）外，其所在行其他0元素标记为划线0
     * @param row
     * @param col
     */
    private void fillRowPrimedZeroExcludeIndex(int row, int col) {
        for(int c = 0; c < nCol; c++) {
            if(costs[row][c] == 0 && masks[row][c] == UNCOVERED && c != col) {
                masks[row][c] = PRIMED_ZERO;
            }
        }
    }

    /**
     * 除了节点（row,col）外，其所在列其他0元素标记为划线0
     * @param row
     * @param col
     */
    private void fillColPrimedZeroExcludeIndex(int row, int col) {
        for(int r = 0; r < nRow; r++) {
            if(costs[r][col] == 0 && masks[r][col] == UNCOVERED && r != row) {
                masks[r][col] = PRIMED_ZERO;
            }
        }
    }

    public AssignmentResult convertResult(){
        clearCovers();
        clearMask();
        findMatch(0);
        List<Integer> matchIndex = new ArrayList<>();
        int totalCost = 0;
        for(int r = 0; r < nRow; r++){
            int matchCol = -1;
            for(int c = 0; c < nCol; c++) {
                if(masks[r][c] == COVERED) {
                    totalCost += costsBackup[r][c];
                    matchCol = c;
                }
            }
            matchIndex.add(matchCol);
        }
        AssignmentResult result = new AssignmentResult();
        result.setWeights(costsBackup);
        result.setTotalWeight(totalCost);
        result.setMatchIndex(matchIndex);
        return result;
    }

    /**
     * 根据已经筛选出来的0元素，从中找出匹配的组合
     * @param row
     * @return
     */
    private boolean findMatch(int row){
        if(row >= nRow) {
            return true;
        }
        //已被覆盖
        if(rowCover[row] == COVERED) {
            System.err.println("指定行已被覆盖，row：" + row);
            return true;
        }
        List<Integer> unCoverZeroColIndex = findRowUnCoverZero(row);
        if(unCoverZeroColIndex.size() == 0) {
            System.err.println("指定行未匹配，row：" + row);
            return true;
        }
        boolean match = false;
        //尝试匹配
        for(int col : unCoverZeroColIndex) {
            if(colCover[col] == COVERED) {
                continue;
            }
            rowCover[row] = COVERED;
            colCover[col] = COVERED;
            masks[row][col] = COVERED;
            match = findMatch(row+1);
            if(match) {
                return true;
            }
            rowCover[row] = UNCOVERED;
            colCover[col] = UNCOVERED;
            masks[row][col] = UNCOVERED;
        }
        return match;

    }

    /**
     *
     * @return
     */
    public void printMatch(){
        System.out.println("----------- match matrix -----------------");
        int totalCost = 0;
        for(int r = 0; r < nRow; r++){
            for(int c = 0; c < nCol; c++) {
                String result = "";
                if(masks[r][c] == COVERED) {
                    result = "*";
                    totalCost += costsBackup[r][c];
                }
                result += costsBackup[r][c];
                System.out.print(String.format("%1$6s", result));
            }
            System.out.print("\n");
        }
        System.out.println("total weight：" + totalCost);
    }

    public void printCostMatrix(){
        System.out.println("----------- cost -----------------");
        for(int r = 0; r < nRow; r++){
            for(int c = 0; c < nCol; c++) {
                String des = costs[r][c]+"";
                if(costs[r][c] == Integer.MAX_VALUE) {
                    des = "MAX";
                }
                System.out.print(String.format("%1$6s", des));
            }
            if(rowCover[r] == COVERED) {
                System.out.print(String.format("%1$6s", "√"));
            }
            System.out.print("\n");
        }
        for(int c = 0; c < nCol; c++) {
            String des = "";
            if(colCover[c] == COVERED) {
                des = "√";
            }
            System.out.print(String.format("%1$6s", des));
        }
        System.out.print("\n");

    }

    private void printMaskMatrix(){
        printLog("----------- mask -----------------\n");
        for(int r = 0; r < nRow; r++){
            for(int c = 0; c < nCol; c++) {
                printLog(String.format("%1$6s", masks[r][c]));
            }
            printLog("\n");
        }
    }

    private void printCover(){
        printLog("----------- cover -----------------\n");
        printLog("rowCover：");
        for(int r = 0; r < nRow; r++) {
            printLog(String.format("%1$6s", rowCover[r]));
        }
        printLog("\ncolCover：");
        for(int c = 0; c < nCol; c++) {
            printLog(String.format("%1$6s", colCover[c]));
        }
        printLog("\n");
    }



    private void printLog(String message) {
        if(debugger) {
            System.out.print(message);
        }
    }
}
