package indi.gxwu.algorithm.assignment;

import lombok.Data;

import java.util.ArrayList;
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

        List<Integer> matchWeights = new ArrayList<>();
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
                matchWeights.add(weights[ii][matchIndex.get(ii)]);
            }
            System.out.print(String.format("%1$3s", desc));
        }
        System.out.print(" ]");
        System.out.print(" total weight：" + totalWeight);

        //获取方差
        if(matchWeights.size() > 0) {
            double avg = ((double) totalWeight)/matchWeights.size();
            double variance = 0;
            for(int weight : matchWeights) {
                variance += Math.pow((weight-avg), 2);
            }
            variance = variance / matchWeights.size();
            variance = Math.sqrt(variance);
            System.out.print(" variance："+variance);
        }

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
