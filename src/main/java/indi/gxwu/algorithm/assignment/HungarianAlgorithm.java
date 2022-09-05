package indi.gxwu.algorithm.assignment;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gx.wu
 * @date: 2022/8/29
 * @description:
 * https://brc2.com/the-algorithm-workshop/
 * 匈牙利算法（通过寻找增广路径，之后反向选取饱和点的路径，获得最佳匹配）
 * n x m 矩阵，不要求 n = m，求解最小权重（求最大权重，可以将所有原权重取反，减去其中最小值）
 * 不适用情况：n x m 对应过来的二分图，必须是每个节点都有路径存在，比如 N0 必须 有 m 条连接到各个 M 节点 M0、M1、M2...Mm
 * n x m 中不存在的路径，不能用 0 或最大值来代表，不存在的路径都有可能被分配
 **/
public class HungarianAlgorithm {

    private int nRow;
    private int nCol;
    private int[][] costs;
    private int[][] costsBackup;
    private int[][] masks;
    private int[] rowCover;
    private int[] colCover;
    private int pathRow0, pathCol0, pathCount;
    private List<MatrixItem> paths = new ArrayList<>();
    private boolean handleMaxCost = false;
    private static final int STEP_ONE = 1;
    private static final int STEP_TWO = 2;
    private static final int STEP_THREE = 3;
    private static final int STEP_FOUR = 4;
    private static final int STEP_FIVE = 5;
    private static final int STEP_SIX = 6;
    private static final int STEP_SEVEN = 7;
    private static final int NONE_ZERO = 0;
    private static final int STARRED_ZERO = 1;
    private static final int PRIMED_ZERO = 2;
    private static final int UNCOVERED = 0;
    private static final int COVERED = 1;

    @Data
    public static class MatrixItem {
        private int row;
        private int col;
    }

    public HungarianAlgorithm(int[][] costs, boolean handleMaxCost){
        this.handleMaxCost = handleMaxCost;
        this.costs = costs;
        nRow = costs.length;
        nCol = costs[0].length;
        masks = new int[nRow][nCol];
        costsBackup = new int[nRow][nCol];
        rowCover = new int[nRow];
        colCover = new int[nCol];
        pathCount = 1;
        int maxValue = Integer.MIN_VALUE;
        for(int r = 0; r < nRow; r++) {
            for(int c = 0; c < nCol; c++) {
                costsBackup[r][c] = costs[r][c];
                if(maxValue < costs[r][c]) {
                    maxValue = costs[r][c];
                }
            }
        }
        //求取最大权值，将权值取反，再加上最大值
        if(handleMaxCost) {
            for(int r = 0; r < nRow; r++) {
                for(int c = 0; c < nCol; c++) {
                    costsBackup[r][c] = costs[r][c];
                    if(maxValue < costs[r][c]) {
                        maxValue = costs[r][c];
                    }
                    costs[r][c] = maxValue - costs[r][c];
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



//                {1, 2, 3},
//                {2, 4, 6},
//                {3, 6, 9},

//                {89, 41, 43, 34, 24, 12, 20},
//                {9, 20, 85, 93, 71, 12, 20},
//                {79, 84, 22, 81, 11, 12, 20},
//                {67, 40, 39, 36, 76, 12, 20},
//                {49, 98, 59, 86, 44, 12, 20},

//                {11, 23, 14, 21, 10},
//                {11, 23, 14, 21, 10},
//                {11, 23, 14, 21, 10},
//                {11, 23, 14, 21, 10},


                {4,     6,     8,     3 },
                {1,     5,     2,     3 },
                {6,     6,     8,     0 },
                {9,     8,     7,     6 },

        };
        HungarianAlgorithm hungarian = new HungarianAlgorithm(costs, true);
        hungarian.runMunkres();
    }

    private void runMunkres(){
        boolean done = false;
        int step = 1;
        printCostMatrix();
        System.out.println("----------- START -----------------");
        int loop = 1;
        while (!done) {
            System.out.println("loop：" + loop + "   step："+step);
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
                    step = stepFive();
                    break;
                case STEP_SIX:
                    step = stepSix();
                    break;
                case STEP_SEVEN:
                    done = true;
                    break;
            }
            printCostMatrix();
            printMaskMatrix();
            printCover();
            loop++;
        }
        finishPrint();
    }

    /**
     * For each row of the matrix, find the smallest element and subtract it from every element in its row.  Go to Step 2.
     * 让每行至少都出现一个 0
     * 遍历矩阵中的每一行，找出当前行最小值，并将当前行所有元素减去最小值，之后跳转步骤 2
     * @return
     */
    private int stepOne(){
        int minInRow, minC;
        for(int r = 0; r < nRow; r++) {
            minInRow = costs[r][0];
            minC = 0;
            //求该行最小元素
            for(int c = 0; c < nCol; c++) {
                if(costs[r][c] < minInRow) {
                    minInRow = costs[r][c];
                    minC = c;
                }
            }
            System.out.println("当前行最小值：" + minInRow +" 所在行/列：" + r +" / " + minC);
            //该行所有元素减去最小值，使每行都至少有一个0元素
            for(int c = 0; c < nCol; c++) {
                costs[r][c] -= minInRow;
            }
        }
        return STEP_TWO;
    }

    /**
     * Find a zero (Z) in the resulting matrix.  If there is no starred zero in its row or column, star Z.
     * Repeat for each element in the matrix. Go to Step 3.
     * 求取标星 0。
     * 遍历矩阵，若元素为 0，且该元素所在行、列均未被标记，则标记该元素为星0，并将所在行、列记为已标记。遍历完矩阵之后，重置行、列标记。
     * @return
     */
    private int stepTwo(){
        for(int r = 0; r < nRow; r++) {
            for(int c = 0; c < nCol; c++) {
                //元素为0，且所在行、列均未被覆盖，元素标记为星0，行、列标记为已覆盖
                if(costs[r][c] == 0 && rowCover[r] == UNCOVERED && colCover[c] == UNCOVERED) {
                    masks[r][c] = STARRED_ZERO;
                    rowCover[r] = COVERED;
                    colCover[c] = COVERED;
                }
            }
        }
        //重置行、列为未覆盖
        for(int r = 0; r < nRow; r++) {
            rowCover[r] = UNCOVERED;
        }
        for(int c = 0; c < nCol; c++) {
            colCover[c] = UNCOVERED;
        }
        return STEP_THREE;
    }

    /**
     * Cover each column containing a starred zero.  If K columns are covered, the starred zeros describe a complete
     * set of unique assignments. In this case, Go to DONE, otherwise, Go to Step 4. Once we have searched the entire
     * cost matrix, we count the number of independent zeros found.  If we have found (and starred) K independent zeros
     * then we are done.  If not we procede to Step 4.
     * 计数查找分配是否结束。
     * 遍历矩阵，根据标记的星0元素，记录列覆盖数据，并计数一共出现列数colCount，若colCount大于矩阵的最大行、或最大列，则认为已经达到最佳匹配，结束计算；
     * 否则跳转步骤4
     * @return
     */
    private int stepThree(){
        for(int r = 0; r < nRow; r++) {
            for(int c = 0; c < nCol; c++) {
                //元素为星0，标记所在列为已覆盖
                if(masks[r][c] == STARRED_ZERO) {
                    colCover[c] = COVERED;
                }
            }
        }
        //计数已覆盖列数
        int colCount = 0;
        for(int c = 0; c < nCol; c++) {
            if(colCover[c] == COVERED) {
                colCount ++;
            }
        }
        //已覆盖列数大于矩阵行数、或列数，代表可以结束了
        if(colCount >= nCol || colCount >= nRow) {
            return STEP_SEVEN;
        }else {
            return STEP_FOUR;
        }
    }

    /**
     * Find a noncovered zero and prime it.  If there is no starred zero in the row containing this primed zero,
     * Go to Step 5. Otherwise, cover this row and uncover the column containing the starred zero.
     * Continue in this manner until there are no uncovered zeros left.
     * Save the smallest uncovered value and Go to Step 6.
     * 求取划线 0。
     * 遍历矩阵，将未覆盖的0，标记为划线0。找出该0元素所在行没有星0元素的坐标。
     * @return
     */
    private int stepFour(){
        int row = -1, col = -1;
        boolean done = false;
        int step = -1;
        while (!done) {
            //查找一个为0，且该元素的行、列的元素均未被覆盖
            MatrixItem target = findAZero();
            row = target.row;
            col = target.col;
            if(row == -1) {
                //未找到直接跳转步骤6
                done = true;
                step = STEP_SIX;
            } else {
                //标记为划线0
                masks[row][col] = PRIMED_ZERO;
                //当前行是否存在星0
                if(starInRow(row)) {
                    //查找当前行星0，所在列的位置
                    col = findStarInRow(row);
                    //标记所在行为已覆盖、所在列为未覆盖
                    rowCover[row] = COVERED;
                    colCover[col] = UNCOVERED;
                }else{
                    done = true;
                    step = STEP_FIVE;
                    pathRow0 = row;
                    pathCol0 = col;
                }
            }
        }
        return step;
    }

    /**
     * Construct a series of alternating primed and starred zeros as follows.
     * Let Z0 represent the uncovered primed zero found in Step 4.
     * Let Z1 denote the starred zero in the column of Z0 (if any).
     * Let Z2 denote the primed zero in the row of Z1 (there will always be one).
     * Continue until the series terminates at a primed zero that has no starred zero in its column.
     * Unstar each starred zero of the series, star each primed zero of the series, erase all primes
     * and uncover every line in the matrix.  Return to Step 3.
     * 求取增广路径。
     * 构建一个划线0、星0交替出现的路径数组，如下：
     * Z0 代表未覆盖的划线0，来着步骤4
     * Z1 代表 Z0 所在列的星0
     * Z2 代表 Z1 所在行的划线0
     * ....
     * 直至划线0所在列没有星0出现。
     * 再将路径数组中，星0元素改为划线0，划线0元素改为星0；
     * 所有行、列覆盖标记重置；
     * 掩码矩阵中所有划线0重置为无标记。
     * @return
     */
    private int stepFive(){
        boolean done = false;
        int r = -1, c = -1;
        pathCount = 1;
        getPath(pathCount-1).row = pathRow0;
        getPath(pathCount-1).col = pathCol0;
        while (!done) {
            r = findStarInCol(getPath(pathCount-1).col);
            if(r > -1) {
                pathCount ++;
                getPath(pathCount-1).row = r;
                getPath(pathCount-1).col = getPath(pathCount-2).col;
            }else{
                done = true;
            }
            if(!done) {
                c = findPrimeInRow(getPath(pathCount-1).row);
                pathCount ++;
                getPath(pathCount-1).row = getPath(pathCount-2).row;
                getPath(pathCount-1).col = c;
            }
        }
        //根据增广路径，将掩码矩阵上，对应节点取反，即星 0 改为 划线 0，划线 0 改为 星 0。
        augmentPath();
        //重置行、列已遍历标识
        clearCovers();
        //移除掩码矩阵中的划线0标记
        erasePrimes();
        printPath();
        return STEP_THREE;
    }

    /**
     * Add the value found in Step 4 to every element of each covered row, and subtract it from every element of each
     * uncovered column. Return to Step 4 without altering any stars, primes, or covered lines.
     * 扩增权值矩阵中的0元素。
     * 从行和列均未覆盖的元素中，找出最小的元素；
     * 遍历矩阵，对已覆盖的行元素，加上最小值；对未覆盖的列元素减去最小值；
     * 跳转步骤4
     * @return
     */
    private int stepSix(){
        int minVal = findSmallest();
        System.out.println("未覆盖元素中，最小值：" + minVal);
        for(int r = 0; r < nRow; r++) {
            for(int c = 0; c < nCol; c++) {
                if(rowCover[r] == COVERED) {
                    costs[r][c] += minVal;
                }
                if(colCover[c] == UNCOVERED){
                    costs[r][c] -= minVal;
                }
            }
        }
        return STEP_FOUR;
    }

    /**
     *
     * @return
     */
    private void finishPrint(){
        System.out.println("----------- DONE -----------------");
        for(int r = 0; r < nRow; r++){
            for(int c = 0; c < nCol; c++) {
                String result = "";
                if(masks[r][c] == STARRED_ZERO) {
                    result = "*";
                }
                result += costsBackup[r][c];
                System.out.print(String.format("%1$6s", result));
            }
            System.out.print("\n");
        }
        printPath();
    }

    private void printCostMatrix(){
        System.out.println("----------- cost -----------------");
        for(int r = 0; r < nRow; r++){
            for(int c = 0; c < nCol; c++) {
                System.out.print(String.format("%1$6s", costs[r][c]));
            }
            System.out.print("\n");
        }
    }

    private void printMaskMatrix(){
        System.out.println("----------- mask -----------------");
        for(int r = 0; r < nRow; r++){
            for(int c = 0; c < nCol; c++) {
                System.out.print(String.format("%1$6s", masks[r][c]));
            }
            System.out.print("\n");
        }
    }

    private void printCover(){
        System.out.println("----------- cover -----------------");
        System.out.println("rowCover：");
        for(int r = 0; r < nRow; r++) {
            System.out.print(String.format("%1$6s", rowCover[r]));
        }
        System.out.println("\ncolCover：");
        for(int c = 0; c < nCol; c++) {
            System.out.print(String.format("%1$6s", colCover[c]));
        }
        System.out.println();
    }

    private void printPath(){
        System.out.println("paths：" + paths);
    }

    /**
     * 若元素等于 0，且所在行、列均未被覆盖时，返回元素的坐标。
     * @return
     */
    private MatrixItem findAZero(){
        int r = 0;
        int c;
        boolean done = false;
        int row = -1, col = -1;
        while (!done) {
            c = 0;
            while (true) {
                if(costs[r][c] == 0 && rowCover[r] == UNCOVERED && colCover[c] == UNCOVERED) {
                    row = r;
                    col = c;
                    done = true;
                }
                c++;
                if(c >= nCol || done) {
                    break;
                }
            }
            r++;
            if(r >= nRow) {
                done = true;
            }
        }
        MatrixItem result = new MatrixItem();
        result.setRow(row);
        result.setCol(col);
        return result;
    }

    /**
     * 判断指定行中，是否包含星0元素
     * @param row
     * @return
     */
    private boolean starInRow(int row) {
        for(int c = 0; c < nCol; c++) {
            if(masks[row][c] == STARRED_ZERO) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找指定行，星0元素所在列
     * @param row
     * @return
     */
    private int findStarInRow(int row){
        int col = -1;
        for(int c = 0; c < nCol; c++) {
            if(masks[row][c] == STARRED_ZERO) {
                col = c;
                return col;
            }
        }
        return col;
    }


    /**
     * 查找指定列中，存在的星0所在行
     * @param c
     * @return
     */
    private int findStarInCol(int c){
        int r = -1;
        for(int i = 0; i < nRow; i++) {
            if(masks[i][c] == STARRED_ZERO) {
                r = i;
                return r;
            }
        }
        return r;
    }

    /**
     * 查找指定行中，存在的划线0元素所在列
     * @param r
     * @return
     */
    private int findPrimeInRow(int r) {
        int c = -1;
        for(int j = 0; j< nCol; j++){
            if(masks[r][j] == PRIMED_ZERO) {
                c = j;
            }
        }
        return c;
    }

    /**
     * 增广路径节点取反
     */
    private void augmentPath() {
        for(int p = 0; p < pathCount; p++) {
            MatrixItem path = getPath(p);
            if(masks[path.row][path.col] == STARRED_ZERO) {
                masks[path.row][path.col] = PRIMED_ZERO;
            }else{
                masks[path.row][path.col] = STARRED_ZERO;
            }
        }
    }

    /**
     * 清空行、列覆盖标记
     */
    private void clearCovers(){
        for(int r = 0; r < nRow; r++) {
            rowCover[r] = UNCOVERED;
        }
        for(int c = 0; c < nCol; c++) {
            colCover[c] = UNCOVERED;
        }
    }

    /**
     * 移除掩码矩阵中的划线0标记
     */
    private void erasePrimes(){
        for(int r = 0; r < nRow; r++) {
            for(int c = 0; c < nCol; c++) {
                if(masks[r][c] == PRIMED_ZERO) {
                    masks[r][c] = NONE_ZERO;
                }
            }
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
     * 根据下标，获取路径元素
     * @param index
     * @return
     */
    private MatrixItem getPath(int index) {
        if(paths.size() <= index) {
            MatrixItem path = new MatrixItem();
            paths.add(path);
            return path;
        }else{
            return paths.get(index);
        }
    }
}
