package indi.gxwu.other;


import static indi.gxwu.other.AVLTree.print;

/**
 * @Author: gx.wu
 * @Date: 2020/1/20 10:35
 * @Description: code something to describe this module what it is
 */
public class ConvertSortedArray2BST {

    public static void main(String[] args) {
        ConvertSortedArray2BST tree = new ConvertSortedArray2BST();
        int[] vals = new int[]{-10,-3,0,5,9};
        TreeNode root = tree.convertSortedArraytoBinarySearchTree(vals);

        String result = print(root);
        System.out.println(result);
    }




    /**
     * @param nums: the sorted array
     * @return: the root of the tree
     */
    public TreeNode convertSortedArraytoBinarySearchTree(int[] nums) {
        // Write your code here.
        return convert(nums, 0, nums.length-1);
    }

    public TreeNode convert(int[] nums, int begin, int end){
        if(begin>end){
            return null;
        }
        int mid = (begin+end)/2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = convert(nums, begin, mid-1);
        node.right = convert(nums, mid+1, end);
        return node;
    }

}
