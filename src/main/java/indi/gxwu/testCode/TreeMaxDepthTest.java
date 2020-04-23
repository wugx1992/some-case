package indi.gxwu.testCode;

/**
 * @Author: gx.wu
 * @Date: 2020/1/3 10:18
 * @Description: 二叉树最大深度
 */
public class TreeMaxDepthTest {

    public static void main(String[] args) {
        TreeMaxDepthTest t = new TreeMaxDepthTest();
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);
        n1.left = n2;
        n1.right = n3;
        n3.left = n4;
        n3.right = n5;
        System.out.println(t.maxDepth(n1));

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
     * @param root: The root of binary tree.
     * @return: An integer
     */
    public int maxDepth(TreeNode root) {
        // write your code here
        return maxDepth(root, 0);
    }

    private int maxDepth(TreeNode node, int currentDepth){
        if(node==null){
            return currentDepth;
        }
        int depth = currentDepth+1;
        int leftDepth = maxDepth(node.left, depth);
        int rightDepth = maxDepth(node.right, depth);
        return leftDepth>rightDepth?leftDepth:rightDepth;
    }


}
