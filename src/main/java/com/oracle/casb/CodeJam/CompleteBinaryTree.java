package com.oracle.casb.CodeJam;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created By : abhijsri
 * Date  : 31/08/18
 **/
public class CompleteBinaryTree {

    private static Map<Integer, List<TreeNode>> cache;

    public CompleteBinaryTree() {
        cache = new HashMap<>();
    }

    public static void main(String[] args) {
        CompleteBinaryTree cbt = new CompleteBinaryTree();
        cbt.solveCBT();
    }

    private void solveCBT() {
        //System.out.println(stringiFy(list.get(0)));
        /*list.stream().forEach(
                System.out::println
        );*/
    }





    private List<TreeNode> allPossibleFBT(int N) {
        if (cache.containsKey(Integer.valueOf(N))) {
            return cache.get(Integer.valueOf(N));
        }
        ImmutableList.Builder<TreeNode> builder = ImmutableList.builder();
        TreeNode rootTreeNode = new TreeNode(0);
        if (N == 1) {
            builder.add(rootTreeNode);
        } else if (N == 3) {
            rootTreeNode.left = new TreeNode(0);
            rootTreeNode.right = new TreeNode(0);
            builder.add(rootTreeNode);
        } else {
            for (int leftWeight = 1; leftWeight <= N - 2; leftWeight += 2) {
                List<TreeNode> leftSubTrees = allPossibleFBT(leftWeight);
                List<TreeNode> rightSubTrees = allPossibleFBT(N - leftWeight - 1);
                for (TreeNode leftTree : leftSubTrees) {
                    for (TreeNode rightTree : rightSubTrees) {
                        TreeNode root = new TreeNode(0);
                        root.left = leftTree;
                        root.right = rightTree;
                        builder.add(root);
                    }
                }
            }
        }
        List<TreeNode> treeList = builder.build();
        cache.put(N, treeList);
        return treeList;
    }

    private class TreeNode {
        TreeNode left;
        TreeNode right;

        int val;

        public TreeNode(int val) {
            this.val = val;
        }
    }
}
