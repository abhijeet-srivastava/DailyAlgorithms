package com.oracle.casb.leetcode;


import java.util.Stack;

/**
 * Definition for a binary tree node.
 */
class BSTIterator {

    TreeNode root;
    Stack<TreeNode> stack;
    public BSTIterator(TreeNode root) {
        stack = new Stack<>();
        //stack.push(root);
        this.root = root;
    }

    /** @return the next smallest number */
    public int next() {
        if (root != null) {
            while(root != null) {
                stack.push(root);
                root = root.left;
            }
        }
        root = stack.pop();
        int res = root.val;
        root = root.right;
        return res;
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return root != null || !stack.isEmpty();
    }
}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */