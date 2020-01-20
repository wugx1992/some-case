package indi.gxwu.other;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: gx.wu
 * @Date: 2020/1/19 16:29
 * @Description: code something to describe this module what it is
 */
public class AVLTree {

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        int[] vals = new int[]{-10,-3,0,5,9};
        TreeNode root = null;
        for (int i=0;i<vals.length;i++){
            root = tree.insertNode(root, vals[i]);
        }
        String result = print(root);
        System.out.println(result);
    }

    public static String print(TreeNode root){
        Map<Integer,StringBuffer> levelResult = new HashMap<Integer, StringBuffer>();
        generateResultStr(root, 1, levelResult);
        int levelIndex = 1;
        StringBuffer result = new StringBuffer();
        while (true){
            if(levelResult.containsKey(levelIndex)){
                String str = levelResult.get(levelIndex).toString();
                if(str.replaceAll("#","").replaceAll(",","").length()!=0){
                    result.append(str);
                    System.out.println(str);
                }
                levelIndex++;
            }else {
                break;
            }
        }
        return result.toString();
    }

    public static void generateResultStr(TreeNode root, int level, Map<Integer,StringBuffer> levelResult){
        StringBuffer levelBuffer;
        if(levelResult.containsKey(level)){
            levelBuffer = levelResult.get(level);
        }else{
            levelBuffer = new StringBuffer();
            levelResult.put(level, levelBuffer);
        }

        if(root==null){
            levelBuffer.append("#,");
        }else{
            levelBuffer.append(root.val).append(",");
            generateResultStr(root.left, level+1, levelResult);
            generateResultStr(root.right, level+1, levelResult);
        }
    }


    public TreeNode insertNode(TreeNode root, int val){
        if(root==null){
            root = new TreeNode(val);
        }else if(root.val>val){
            root.left = insertNode(root.left, val);
            if(Math.abs(height(root.left)-height(root.right))>1){
                root = root.left.val>val?ll(root):lr(root);
            }
        }else{
            root.right = insertNode(root.right, val);
            if(Math.abs(height(root.left)-height(root.right))>1){
                root = root.right.val>val?rl(root):rr(root);
            }
        }
        return root;
    }



    public int height(TreeNode root){
        if(root==null){
            return 0;
        }
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        return leftHeight>rightHeight?leftHeight+1:rightHeight+1;
    }

    public TreeNode ll(TreeNode root){
        TreeNode leftSon = root.left;
        root.left = leftSon.right;
        leftSon.right = root;
        return leftSon;
    }


    public TreeNode rr(TreeNode root){
        TreeNode rightSon = root.right;
        root.right = rightSon.left;
        rightSon.left = root;
        return rightSon;
    }


    public TreeNode lr(TreeNode root){
        root.left = rr(root.left);
        return ll(root);
    }


    public TreeNode rl(TreeNode root){
        root.right = ll(root.right);
        return rr(root);
    }

}
