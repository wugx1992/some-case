package indi.gxwu.algorithm.assignment;

import lombok.Data;

import java.util.List;

/**
 * @author: gx.wu
 * @date: 2022/9/6
 * @description:
 **/
@Data
public class AssignmentResult {
    private List<Integer> matchIndex;
    private int totalWeight;
    private int[][] weights;

    public void printMatchResult(){
        System.out.print(" [");
        for(int ii = 0; ii < matchIndex.size(); ii++) {
            if(ii > 0) {
                System.out.print(", ");
            }
            System.out.print(String.format("%1$3s", matchIndex.get(ii)));
        }
        System.out.print(" ]");

        System.out.print(" [");
        for(int ii = 0; ii < matchIndex.size(); ii++) {
            if(ii > 0) {
                System.out.print(", ");
            }
            String desc;
            if(matchIndex.get(ii) == -1) {
                desc = "-1";
            }else{
                desc = weights[ii][matchIndex.get(ii)]+"";
            }
            System.out.print(String.format("%1$3s", desc));
        }
        System.out.print(" ]");
        System.out.print(" total weight：" + totalWeight);
        System.out.println();

    }

    public void printMatchMatrix(){
        int nRow = weights.length;
        int nCol = weights[0].length;
        for(int r = 0; r < nRow; r++){
            int matchCol = -1;
            if(matchIndex.size() > r) {
                matchCol = matchIndex.get(r);
            }
            for(int c = 0; c < nCol; c++) {
                String result = "";
                if(matchCol == c) {
                    result = "*";
                }
                result += weights[r][c];
                System.out.print(String.format("%1$6s", result));
            }
            System.out.print("\n");
        }
    }

}
