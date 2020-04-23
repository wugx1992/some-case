package indi.gxwu.testCode;

import java.util.*;

/**
 * @Author: gx.wu
 * @Date: 2020/1/3 16:33
 * @Description: code something to describe this module what it is
 */
public class TreeLevelOrderTest {


    public static void main(String[] args) {
        TreeLevelOrderTest t = new TreeLevelOrderTest();
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);
        TreeNode n6 = new TreeNode(6);
        TreeNode n7 = new TreeNode(7);
        TreeNode n8 = new TreeNode(8);
        TreeNode n9 = new TreeNode(9);

        n1.left = n2;
        n1.right = n3;
        n3.left = n4;
        n3.right = n5;
        n4.left = n6;
        n4.right = n7;
        n5.left = n8;
        n5.right = n9;

        List<List<Integer>> result = t.levelOrder(n1);
        System.out.println(result);

    }



    public static class TreeNode {
        public int val;
        public TreeNode left, right;
        public TreeNode(int val) {
            this.val = val;
            this.left = this.right = null;
        }
    }


    /**
     * @param root: A Tree
     * @return: Level order a list of lists of integer
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        // write your code here
        if(root==null){
            return new ArrayList<List<Integer>>();
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        Map<Integer,Integer> nodeDepthMark = new HashMap<Integer, Integer>();
        queue.add(root);
        Map<Integer,List<Integer>> depthValue = new HashMap<Integer, List<Integer>>();
        nodeDepthMark.put(root.hashCode(), 1);
        while (!queue.isEmpty()){
            TreeNode node = queue.poll();
            int depth = nodeDepthMark.get(node.hashCode());
            List<Integer> value;
            if(depthValue.containsKey(depth)){
                value = depthValue.get(depth);
            }else{
                value = new ArrayList<Integer>();
                depthValue.put(depth, value);
            }
            value.add(node.val);
            if(node.left!=null){
                queue.add(node.left);
                nodeDepthMark.put(node.left.hashCode(), depth+1);
            }
            if(node.right!=null){
                queue.add(node.right);
                nodeDepthMark.put(node.right.hashCode(), depth+1);
            }
        }
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        int i=1;
        while (true){
            if(depthValue.containsKey(i)){
                result.add(depthValue.get(i));
                i++;
            }else {
                break;
            }
        }
        return result;

    }
}
