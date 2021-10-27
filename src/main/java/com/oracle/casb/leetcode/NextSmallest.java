package com.oracle.casb.leetcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class NextSmallest {

    public static void main(String[] args) {

    }


    public ArrayList<Integer> prevSmaller(ArrayList<Integer> arr) {
        ArrayList<Integer> res = new ArrayList<>();
        TreeNode root = null;
        for (int i = 0; i < arr.size(); i++) {
            res.add(insertAndGetPrecedessor(root, arr.get(i)));
        }
        return res;
    }

    private List<Integer> iterativeInorder(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();
        if (root == null) {
            return inorder;
        }
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            inorder.add(root.val);
            root = root.right;
        }
        return inorder;
    }

    private int kthSmallest(TreeNode root, int k) {
        if (root == null) {
            return -1;
        }
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (--k == 0) {
                return root.val;
            }
            root = root.right;
        }
        return -1;
    }
    private boolean isValidBst(TreeNode root) {
        boolean isSorted = true;
        if (root == null) {
            return isSorted;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode prev = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.peek();
            if (prev != null && prev.val > root.val) {
                isSorted = false;
                break;
            }
            prev = root;
            root = root.right;
        }
        return isSorted;
    }

    private List<Integer> iterativePostOrder1Stack(TreeNode root) {
        List<Integer> postorder = new ArrayList<>();
        if (root == null) {
            return postorder;
        }
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                if (root.left != null) {
                    root = root.left;
                } else {
                    root = root.right;
                }
            }
            TreeNode node =  stack.pop();
            postorder.add(node.val);
            if (!stack.isEmpty() && stack.peek().left == node) {
                root = stack.peek().right;
            }
        }
        return postorder;
    }
    private List<TreeNode> iterativePostOrder2Stack(TreeNode root) {
        //Left -> Right -> Node
        List<TreeNode> postorder = new ArrayList<>();
        if (root == null) {
            return postorder;
        }
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            root = stack1.pop();
            stack2.push(root);
            if (root.left != null) {
                stack1.push(root.left);
            }
            if (root.right != null) {
                stack1.push(root.right);
            }
        }
        while (!stack2.isEmpty()) {
            postorder.add(stack2.pop());
        }
        return postorder;
    }
    private List<TreeNode> iterativePreOrder(TreeNode root) {
        //root -> Left -> Right
        List<TreeNode> preorder = new ArrayList<>();
        if (root == null) {
            return preorder;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            root = stack.pop();
            preorder.add(root);
            if (root.right != null) {
                stack.push(root.right);
            }
            if (root.left != null) {
                stack.push(root.left);
            }
        }
        return preorder;
    }

    public int twoCitySchedCost(int[][] costs) {
        int totalCost = 0;
        Arrays.sort(costs, (personACost, personBCost)
                -> ((personACost[0] - personACost[1]) - (personBCost[0] - personBCost[1])));
        int N = costs.length/2;
        int index = 0;
        for (int  i = 0; i < costs.length; i++) {
            if(i == N) {
                index = 1;
            }
            totalCost += costs[i][index];
        }
        return totalCost;
    }

    private List<Integer> postOrderTraversal(TreeNode root) {
        LinkedList<Integer> postorder = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || stack.isEmpty()) {
            if (root != null) {
                stack.push(root);
                postorder.addFirst(root.val);// Reverse the process of preorder
                root = root.right;// Reverse the process of preorder
            } else {
                root = stack.pop();
                root = root.left;// Reverse the process of preorder
            }
        }
        return postorder;
    }
    private List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> preorder = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            if(root != null) {
                stack.push(root);
                preorder.add(root.val);//Process before going to child
                root = root.left;
            } else {
                root = stack.pop();
                root = root.right;
            }
        }
        return preorder;
    }

    private List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            if(root != null) {
                stack.push(root);
                root = root.left;
            } else {
                root = stack.pop();
                inorder.add(root.val);
                root = root.right;
            }
        }
        return inorder;
    }
    private int insertAndGetPrecedessor(TreeNode root, Integer integer) {
        int precedessor = -1;
        if (root == null) {
            return precedessor;
        }
        while (root.left != null) {

        }
        return precedessor;
    }

    public int testRead() {
        int count = 0;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int M = Integer.valueOf(br.readLine());
            int N = Integer.valueOf(br.readLine());
            int P = Integer.valueOf(br.readLine());
            int Q = Integer.valueOf(br.readLine());
            int[][] cache = new int[N-M+1][Q-P+1];

            for (int i = M; i <= N; i++) {
                for (int j = P; j<= Q;j++) {
                    count += getCount(i, j, cache);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    private int getCount(int i, int j, int[][] cache) {
        if (cache[i][j] != 0) {
            return cache[i][j];
        }
        int count = 0;
        if (i == j) {
            count = 1;
        } else if (i == 2*j || j == 2*i) {
            count = 2;
        } else {
            int min = Math.min(i, j);
            int max = Math.min(i, j);
            count = 1 + getCount(max - min, min, cache);
        }
        cache[i][j] = count;
        return count;
    }

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }
}
