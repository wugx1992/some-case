package indi.gxwu.other;

/**
 * @Author: gx.wu
 * @Date: 2020/1/20 11:29
 * @Description: 给定一个非空的特别的结点包含非负值二叉树，其中树中的每一个节点包含正好两个或者零个子结点。如果这个结点有两个子结点，那么这个结点的值不大于它的两个子结点。
 * 对于这样一个二叉树，你需要输出由整个树当中的结点值构成的集合中的次小值。
 * 如果不存在这样的一个次小值，输出-1作为替代。
 * 输入:
 *     2
 *    / \
 *   2   5
 *      / \
 *     5   7
 *
 * 输出: 5
 * 解释: 最小的值是2，次小值是5.
 */
public class FindSecondMinimumValueBST {
    public static void main(String[] args) {
        FindSecondMinimumValueBST t = new FindSecondMinimumValueBST();
        TreeNode tree1;
        TreeNode node11 = new TreeNode(2);
        TreeNode tree12 = new TreeNode(2);
        TreeNode tree13 = new TreeNode(5);
        TreeNode tree14 = new TreeNode(5);
        TreeNode tree15 = new TreeNode(7);
        tree1 = node11;
        tree1.left = tree12;
        tree1.right = tree13;
        tree13.left = tree14;
        tree13.right = tree15;
        System.out.println(t.findSecondMinimumValue(tree1));


        TreeNode tree2;
        TreeNode node21 = new TreeNode(2);
        TreeNode tree22 = new TreeNode(2);
        TreeNode tree23 = new TreeNode(2);
        tree2 = node21;
        tree2.left = tree22;
        tree2.right = tree23;
        System.out.println(t.findSecondMinimumValue(tree2));


    }

    /**
     * @param root: the root
     * @return: the second minimum value in the set made of all the nodes' value in the whole tree
     */
    public int findSecondMinimumValue(TreeNode root) {
        // Write your code here
        if(root==null || root.right==null || root.left==null || root.right==null){
            return -1;
        }
        boolean minIsRight = root.left.val>=root.right.val?true:false;
        if(minIsRight){
            if(root.left.val == root.val){
                return -1;
            }else if(root.right.val == root.val){
               return root.left.val;
            }else {
                return root.right.val;
            }
        }else{
            if(root.left.val == root.val){
                return root.right.val;
            }else{
                return root.left.val;
            }
        }
    }
}
