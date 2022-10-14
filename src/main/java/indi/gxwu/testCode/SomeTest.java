package indi.gxwu.testCode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: gx.wu
 * @date: 2022/10/11
 * @description:
 **/
public class SomeTest {


    @Test
    public void areAlmostEqualTest(){
        String s1, s2;
        s1 = "bank"; s2 = "kanb";
        assert areAlmostEqual(s1, s2) == true;
        s1 = "attack";
        s2 = "defend";
        assert areAlmostEqual(s1, s2) == false;
        s1 = "kelb";
        s2 = "kelb";
        assert areAlmostEqual(s1, s2) == true;
        s1 = "abcd";
        s2 = "dcba";
        assert areAlmostEqual(s1, s2) == false;
        s1 = "aa";
        s2 = "ac";
        assert areAlmostEqual(s1, s2) == false;
        s1 = "caa";
        s2 = "aaz";
        assert areAlmostEqual(s1, s2) == false;
    }

    /**
     * 若只交互一个字符，使得两个字符串相同，返回true，否则返回false
     * 前提：s1.length() == s2.length()
     * @param s1
     * @param s2
     * @return
     */
    public boolean areAlmostEqual(String s1, String s2) {
        boolean result = true;
        char tmp1 = 0, tmp2 = 0;
        int count = 0;
        for(int i = 0; i<s1.length() && count <=2;i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            if(c1 == c2) {
                continue;
            }
            if(tmp1 == 0) {
                tmp1 = c1;
                tmp2 = c2;
            }else{
                if(tmp1 != c2 || tmp2 != c1) {
                    result = false;
                    break;
                }
            }
            count++;
        }
        return result && (count ==2 || count == 0);
    }


    @Test
    public void minChangesTest(){
        int[] nums;
        int k;
        nums = new int[]{26,19,19,28,13,14,6,25,28,19,0,15,25,11};
        k = 3;
        assert minChanges3(nums, k) == 11;
//
//        nums = new int[]{1,2,0,3,0};
//        k = 1;
//        assert minChanges(nums, k) == 3;
//
//        nums = new int[]{3,4,5,2,1,7,3,4,7};
//        k = 3;
//        assert minChanges(nums, k) == 3;
//
//        nums = new int[]{1,2,4,1,2,5,1,2,6};
//        k = 3;
//        assert minChanges(nums, k) == 3;
    }


    /**
     * 给你一个整数数组 nums 和一个整数 k 。
     * 区间 [left, right]（left <= right）的 异或结果 是对下标位于 left 和 right（包括 left 和 right ）之间所有元素进行 XOR
     * 运算的结果：nums[left] XOR nums[left+1] XOR ... XOR nums[right] 。
     *
     * 返回数组中 要更改的最小元素数 ，以使所有长度为 k 的区间异或结果等于零。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/make-the-xor-of-all-segments-equal-to-zero
     * @param nums
     * @param k
     * @return
     */
    public int minChanges(int[] nums, int k) {
        int result = 0, t;
        for(int i = 0; i < nums.length; i++) {
            if(k == 1 && nums[i] != 0) {
                result ++;
                continue;
            }
            t = nums[i];
            if(i+k>nums.length) {
                break;
            }
            for(int j = 1; j < k-1; j++) {
                t = t^nums[i+j];
            }
            if(t != nums[i+k-1]){
                result++;
                nums[i+k-1] = t;
            }
        }
        return result;
    }

    public int minChanges2(int[] nums, int k) {
        int result = 0, t, nn = 0;
        if(k == 1) {
            for(int i = 0; i < nums.length; i++) {
                if(nums[i] != 0) {
                    result ++;
                }
            }
            return result;
        }

        int[] backups = new int[nums.length];
        for(int i = 0; i < nums.length; i++) {
            backups[i] = nums[i];
        }

        t = nums[0];
        boolean fc = true;
        for(int i = 1; i < k-1; i++) {
            t = t^nums[i];
        }
        if(t != nums[k-1]){
            fc = false;
        }
        if(fc) {
            for(int i = 0; i < nums.length; i++) {
                if(k == 1 && nums[i] != 0) {
                    result ++;
                    continue;
                }
                t = nums[i];
                if(i+k>nums.length) {
                    break;
                }
                for(int j = 1; j < k-1; j++) {
                    t = t^nums[i+j];
                }
                if(t != nums[i+k-1]){
                    result++;
                    nums[i+k-1] = t;
                }
            }
        }else{
            int r = 0;
            result = nums.length;
            for(int lo = 0; lo < k; lo++) {
                for(int i = 0; i < nums.length; i++) {
                    nums[i] = backups[i];
                }
                t = -1;
                for(int i = 0; i < k; i++) {
                    if(i == lo){
                        continue;
                    }
                    if(t == -1) {
                        t = nums[i];
                    }else{
                        t = t^nums[i];
                    }
                }
                r = 1;
                nums[lo] = t;
                for(int i = 1; i < nums.length; i++) {
                    t = nums[i];
                    if(i+k>nums.length) {
                        break;
                    }
                    for(int j = 1; j < k-1; j++) {
                        t = t^nums[i+j];
                    }
                    if(t != nums[i+k-1]){
                        r++;
                        nums[i+k-1] = t;
                    }
                }
                if(r < result) {
                    result = r;
                }
            }
        }

        return result;
    }


    public int minChanges3(int[] nums, int k) {
        int result = 0, t, nn = 0;
        int group = nums.length/k;
        int surplus = nums.length%k;
        int totalGroup = (nums.length + k -1)/k;
        Map<Integer,Map<Integer, Integer>> groupAppearNumCountMap = new HashMap<>();
        int[][] groupsCount = new int[k][2];
        for(int i = 0; i < k; i++) {
            Map<Integer, Integer> appearNumCountMap = new HashMap<>();
            for(int g = 0; g< totalGroup; g++) {
                int index = i+g*k;
                if(index >= nums.length) {
                    continue;
                }
                int num = nums[index];
                int count = 1;
                if(appearNumCountMap.containsKey(num)) {
                 count += appearNumCountMap.get(num);
                }
                appearNumCountMap.put(num, count);
            }
            int maxCount = Integer.MIN_VALUE, targetValue = 0;
            for(Map.Entry<Integer,Integer> entity : appearNumCountMap.entrySet()) {
                if(entity.getValue()>maxCount) {
                    maxCount = entity.getValue();
                    targetValue = entity.getKey();
                }
            }
            groupsCount[i][0] = maxCount;
            groupsCount[i][1] = targetValue;
            groupAppearNumCountMap.put(i, appearNumCountMap);
        }
        int minCount = Integer.MAX_VALUE;
        for(int i = 0; i<k; i++) {
            int count = group - groupsCount[i][0];
            if(surplus>0) {
                count++;
            }
            if(minCount > count) {
                minCount = count;
            }
        }
        result = minCount;
        return result;
    }

//    public int check(Map<Integer,Map<Integer, Integer>> dataMap, int group, int cost){
//        if(dataMap.keySet().size() <= group) {
//            return 0;
//        }
//        int minCost = Integer.MAX_VALUE;
//        Map<Integer, Integer> numCountMap = dataMap.get(group);
//        for(Map.Entry<Integer,Integer> entry : numCountMap.entrySet()) {
//
//        }
//    }


    @Test
    public void findCenterTest(){
        int[][] edges;
        edges = new int[][]{{1,2},{2,3},{4,2}};
        assert findCenter(edges) == 2;

        edges = new int[][]{{1,2},{5,1},{1,3},{1,4}};
        assert findCenter(edges) == 1;
    }

    /**
     * 有一个无向的 星型 图，由 n 个编号从 1 到 n 的节点组成。星型图有一个 中心 节点，并且恰有 n - 1 条边将中心节点与其他每个节点连接起来。
     *
     * 给你一个二维整数数组 edges ，其中 edges[i] = [ui, vi] 表示在节点 ui 和 vi 之间存在一条边。请你找出并返回 edges 所表示星型图的中心节点。
     * @param edges
     * @return
     */
    public int findCenter(int[][] edges) {
        Map<Integer,Integer> nodeCount = new HashMap<>();
        int maxCount = Integer.MIN_VALUE;
        int node = 0;
        for(int[] edge : edges) {
            int count = 1;
            if(nodeCount.containsKey(edge[0])) {
                count = nodeCount.get(edge[0])+1;
            }
            nodeCount.put(edge[0], count);
            if(maxCount < count) {
                node = edge[0];
                maxCount = count;
            }
            count = 1;
            if(nodeCount.containsKey(edge[1])) {
                count = nodeCount.get(edge[1])+1;
            }
            nodeCount.put(edge[1], count);
            if(maxCount < count) {
                node = edge[1];
                maxCount = count;
            }
        }
        return node;
    }





}
